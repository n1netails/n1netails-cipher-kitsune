package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.EncryptionStrategy;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

@Component
@ConditionalOnProperty(name = "n1netails.encryption.chacha20.enabled", havingValue = "true", matchIfMissing = true)
public class ChaCha20Strategy implements EncryptionStrategy {

    static {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    private static final String ALGO = "ChaCha20-Poly1305/None/NoPadding";
    private static final int IV_LENGTH_BYTE = 12;

    @Override
    public String encrypt(String data, String key) throws Exception {
        byte[] iv = new byte[IV_LENGTH_BYTE];
        new SecureRandom().nextBytes(iv);

        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "ChaCha20");
        Cipher cipher = Cipher.getInstance(ALGO, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));

        byte[] encrypted = cipher.doFinal(data.getBytes());
        byte[] result = ByteBuffer.allocate(iv.length + encrypted.length)
                .put(iv)
                .put(encrypted)
                .array();
        return Base64.getEncoder().encodeToString(result);
    }

    @Override
    public String decrypt(String encryptedData, String key) throws Exception {
        byte[] decoded = Base64.getDecoder().decode(encryptedData);
        ByteBuffer bb = ByteBuffer.wrap(decoded);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        bb.get(iv);
        byte[] encrypted = new byte[bb.remaining()];
        bb.get(encrypted);

        SecretKeySpec secretKey = new SecretKeySpec(Base64.getDecoder().decode(key), "ChaCha20");
        Cipher cipher = Cipher.getInstance(ALGO, "BC");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));

        return new String(cipher.doFinal(encrypted));
    }

    @Override
    public String getAlgorithmName() {
        return "ChaCha20-Poly1305";
    }
}
