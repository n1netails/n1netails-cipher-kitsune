package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.EncryptionStrategy;
import com.n1netails.n1netails.cipher.kitsune.util.AsymmetricKeyGenerator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@ConditionalOnProperty(name = "n1netails.encryption.rsa-oaep.enabled", havingValue = "true", matchIfMissing = true)
public class RsaOaepStrategy implements EncryptionStrategy {

    private static final String ALGO = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";

    @Override
    public String encrypt(String data, String key) throws Exception {
        String cleanKey = AsymmetricKeyGenerator.cleanPem(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.getDecoder().decode(cleanKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PublicKey publicKey = kf.generatePublic(spec);

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    @Override
    public String decrypt(String encryptedData, String key) throws Exception {
        String cleanKey = AsymmetricKeyGenerator.cleanPem(key);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(cleanKey));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = kf.generatePrivate(spec);

        Cipher cipher = Cipher.getInstance(ALGO);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData)));
    }

    @Override
    public String getAlgorithmName() {
        return "RSA-OAEP";
    }
}
