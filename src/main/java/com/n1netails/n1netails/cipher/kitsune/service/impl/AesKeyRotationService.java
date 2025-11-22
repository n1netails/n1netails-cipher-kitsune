package com.n1netails.n1netails.cipher.kitsune.service.impl;

import com.n1netails.n1netails.cipher.kitsune.service.KeyRotationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

@Slf4j
@Service
public class AesKeyRotationService implements KeyRotationService {

    private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
    private static final int TAG_LENGTH_BIT = 128;
    private static final int IV_LENGTH_BYTE = 12;

    @Value("${n1netails.encryption.old.key}")
    private String oldKeyString;
    @Value("${n1netails.encryption.new.key}")
    private String newKeyString;

    private SecretKey oldKey;
    private SecretKey newKey;

    @PostConstruct
    public void init() {
        oldKey = decodeKey(oldKeyString);
        newKey = decodeKey(newKeyString);
        log.info("Key rotation service initialized successfully.");
    }

    private SecretKey decodeKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    @Override
    public String rotate(String encryptedData) throws Exception {
        // 1. Decrypt with old key
        String decrypted = decrypt(encryptedData, oldKey);

        // 2. Encrypt with new key
        String reEncrypted = encrypt(decrypted, newKey);

        return reEncrypted;
    }

    private String encrypt(String data, SecretKey key) throws Exception {
        byte[] iv = new byte[IV_LENGTH_BYTE];
        new SecureRandom().nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] encryptedData = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        byte[] byteBuffer = ByteBuffer.allocate(iv.length + encryptedData.length)
                .put(iv)
                .put(encryptedData)
                .array();

        return Base64.getEncoder().encodeToString(byteBuffer);
    }

    private String decrypt(String encryptedData, SecretKey key) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);

        ByteBuffer byteBuffer = ByteBuffer.wrap(decodedData);
        byte[] iv = new byte[IV_LENGTH_BYTE];
        byteBuffer.get(iv);

        byte[] encryptedBytes = new byte[byteBuffer.remaining()];
        byteBuffer.get(encryptedBytes);

        Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
