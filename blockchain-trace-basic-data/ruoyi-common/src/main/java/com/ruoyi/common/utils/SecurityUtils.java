package com.ruoyi.common.utils;

import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.CustomException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.Key;

/**
 * 安全服务工具类
 * 
 * @author ruoyi
 */
public class SecurityUtils
{
    private static BouncyCastleProvider provider = new BouncyCastleProvider();
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS7Padding";
    /**
     * 获取用户账户
     **/
    public static String getUsername()
    {
        try
        {
            return getLoginUser().getUsername();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户账户异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        try
        {
            return (LoginUser) getAuthentication().getPrincipal();
        }
        catch (Exception e)
        {
            throw new CustomException("获取用户信息异常", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication()
    {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 生成BCryptPasswordEncoder密码
     *
     * @param password 密码
     * @return 加密字符串
     */
    public static String encryptPassword(String password)
    {
        // password 使用sm3 散列 再使用sm4加密
        byte[] hashed = digest(password.getBytes(StandardCharsets.UTF_8));
        try {
            byte[] encrypted = symmEcb(hashed, "1234567812345678".getBytes(StandardCharsets.UTF_8), false);
            String passwordChanged = Hex.toHexString(encrypted);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.encode(passwordChanged);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断密码是否相同
     *
     * @param rawPassword 真实密码
     * @param encodedPassword 加密后字符
     * @return 结果
     */
    public static boolean matchesPassword(String rawPassword, String encodedPassword)
    {
        byte[] hashed = digest(rawPassword.getBytes(StandardCharsets.UTF_8));
        try {
            byte[] encrypted = symmEcb(hashed, "1234567812345678".getBytes(StandardCharsets.UTF_8), false);
            String passwordChanged = Hex.toHexString(encrypted);
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            return passwordEncoder.matches(passwordChanged, encodedPassword);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static byte[] digest(byte[] msg) {
        SM3Digest sm3Digest = new SM3Digest();
        sm3Digest.update(msg, 0, msg.length);
        byte[] hash = new byte[sm3Digest.getDigestSize()];
        sm3Digest.doFinal(hash, 0);
        return hash;
    }


    public static byte[] symmEcb(byte[] msg, byte[] key, boolean mode) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING, provider);
        Key sm4Key = new SecretKeySpec(key, "SM4");
        if (!mode) {
            cipher.init(Cipher.ENCRYPT_MODE, sm4Key);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, sm4Key);
        }

        return cipher.doFinal(msg);
    }
    /**
     * 是否为管理员
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public static boolean isAdmin(Long userId)
    {
        return userId != null && 1L == userId;
    }

    public static void main(String[] args) {
        String k = encryptPassword("123456");
        boolean r = matchesPassword("123456", k);
        System.out.println(k);
        System.out.println(r);
    }
}
