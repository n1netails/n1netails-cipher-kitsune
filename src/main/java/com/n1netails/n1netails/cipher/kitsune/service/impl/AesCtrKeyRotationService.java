package com.n1netails.n1netails.cipher.kitsune.service.impl;

import com.n1netails.n1netails.cipher.kitsune.model.KeyRotationType;
import com.n1netails.n1netails.cipher.kitsune.service.KeyRotationService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;

@Slf4j
@Service
public class AesCtrKeyRotationService implements KeyRotationService {

    private static final String ALGORITHM = "AES/CTR/NoPadding";

    @Value("${n1netails.encryption.old.key}")
    private String oldKeyString;
    @Value("${n1netails.encryption.new.key}")
    private String newKeyString;

    private SecretKey oldKey;
    private SecretKey newKey;

    @PostConstruct
    public void init() {
        Security.addProvider(new BouncyCastleProvider());
        oldKey = decodeKey(oldKeyString);
        newKey = decodeKey(newKeyString);
        log.info("AES-CTR key rotation service initialized successfully.");
    }

    private SecretKey decodeKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "AES");
    }

    @Override
    public String rotate(String encryptedData) throws Exception {
        String decrypted = decrypt(encryptedData);
        return encrypt(decrypted);
    }

    @Override
    public KeyRotationType getType() {
        return KeyRotationType.AES_CTR;
    }

    private String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        byte[] iv = new byte[cipher.getBlockSize()];
        new java.security.SecureRandom().nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, newKey, new IvParameterSpec(iv));
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        byte[] combined = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, combined, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, combined, iv.length, encryptedBytes.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    private String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] iv = new byte[cipher.getBlockSize()];
        System.arraycopy(decodedData, 0, iv, 0, iv.length);
        cipher.init(Cipher.DECRYPT_MODE, oldKey, new IvParameterSpec(iv));
        byte[] encryptedBytes = new byte[decodedData.length - iv.length];
        System.arraycopy(decodedData, iv.length, encryptedBytes, 0, encryptedBytes.length);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }
}
