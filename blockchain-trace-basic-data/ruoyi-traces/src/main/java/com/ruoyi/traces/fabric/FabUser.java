package com.ruoyi.traces.fabric;

import org.hyperledger.fabric.sdk.Enrollment;
import org.hyperledger.fabric.sdk.User;
import org.hyperledger.fabric.sdk.exception.CryptoException;
import org.hyperledger.fabric.sdk.identity.X509Enrollment;
import org.hyperledger.fabric.sdk.security.CryptoPrimitives;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.util.Set;


public class FabUser implements User {
    private FabUser() {

    }

    FabUser(String name, String mspId, String keyFile, String certFile) {
        if ((this.enrollment = loadKeyAndCert(keyFile, certFile)) != null) {
            this.name = name;
            this.mspId = mspId;
        }
    }

    private String name;
    private String account;
    private String affiliation;
    private String mspId;
    private Set<String> roles;
    private Enrollment enrollment;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Set<String> getRoles() {
        return this.roles;
    }

    @Override
    public String getAccount() {
        return this.account;
    }

    @Override
    public String getAffiliation() {
        return this.affiliation;
    }

    @Override
    public Enrollment getEnrollment() {
        return this.enrollment;
    }

    @Override
    public String getMspId() {
        return this.mspId;
    }

    private Enrollment loadKeyAndCert(String keyFile, String certFile) {
        byte[] keyPem;
        try {
            keyPem = Files.readAllBytes(Paths.get(Const.BASE_PATH + keyFile));
            byte[] certPem = Files.readAllBytes(Paths.get(Const.BASE_PATH + certFile));
            CryptoPrimitives primitives = new CryptoPrimitives();
            PrivateKey privateKey = primitives.bytesToPrivateKey(keyPem);
            return new X509Enrollment(privateKey, new String(certPem));
        } catch (IOException | IllegalAccessException | InstantiationException | CryptoException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
