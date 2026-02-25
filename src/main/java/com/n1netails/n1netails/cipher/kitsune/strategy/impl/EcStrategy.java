package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.EncryptionStrategy;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@ConditionalOnProperty(name = "n1netails.encryption.ec.enabled", havingValue = "true", matchIfMissing = true)
public class EcStrategy implements EncryptionStrategy {

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private static final String ALGO = "ECIES";

    @Override
    public String encrypt(String data, String key) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
        KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        PublicKey publicKey = kf.generatePublic(spec);

        Cipher cipher = Cipher.getInstance(ALGO, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    @Override
    public String decrypt(String encryptedData, String key) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key));
        KeyFactory kf = KeyFactory.getInstance("EC", "BC");
        PrivateKey privateKey = kf.generatePrivate(spec);

        Cipher cipher = Cipher.getInstance(ALGO, "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
    }

    @Override
    public String getAlgorithmName() {
        return "EC-ECIES";
    }
}
