package com.ruoyi.framework.web.service;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.CustomException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;

/**
 * 登录校验方法
 * 
 * @author ruoyi
 */
@Component
public class SysLoginService
{
    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private RedisCache redisCache;

    private static BouncyCastleProvider provider = new BouncyCastleProvider();
    /**
     * 登录验证
     * 
     * @param username 用户名
     * @param password 密码
     * @param code 验证码
     * @param uuid 唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid)
    {
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null)
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha))
        {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }

        // 用户验证
        LoginUser loginUser;
        try
        {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            byte[] hashed = digest(password.getBytes(StandardCharsets.UTF_8));
            byte[] encrypted = symmEcb(hashed, "1234567812345678".getBytes(StandardCharsets.UTF_8), false);
            String passwordChanged = Hex.toHexString(encrypted);

            loginUser = (LoginUser) userDetailsService.loadUserByUsername(username);

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
             boolean r = passwordEncoder.matches(passwordChanged, loginUser.getPassword());
             if (!r) {
                 throw new BadCredentialsException("not match");
             }
        }
        catch (Exception e)
        {
            if (e instanceof BadCredentialsException)
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            }
            else
            {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new CustomException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));

        // 生成token
        return tokenService.createToken(loginUser);
    }

    public static byte[] digest(byte[] msg) {
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(msg, 0, msg.length);
        byte[] hash = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(hash, 0);
        return hash;
    }


    public static byte[] symmEcb(byte[] msg, byte[] key, boolean mode) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS7Padding", provider);
        Key sm4Key = new SecretKeySpec(key, "SM4");
        if (!mode) {
            cipher.init(Cipher.ENCRYPT_MODE, sm4Key);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, sm4Key);
        }

        return cipher.doFinal(msg);
    }
}
