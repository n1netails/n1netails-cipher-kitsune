package com.n1netails.n1netails.cipher.kitsune.service.impl;

import com.n1netails.n1netails.cipher.kitsune.model.KeyRotationType;
import com.n1netails.n1netails.cipher.kitsune.service.KeyRotationService;
import com.n1netails.n1netails.cipher.kitsune.util.AesCipherUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Service
public class DesToAesKeyRotationService implements KeyRotationService {

    private static final String DES_ENCRYPT_ALGO = "DES/ECB/PKCS5Padding";

    @Value("${n1netails.encryption.old.key}")
    private String oldKeyString;
    @Value("${n1netails.encryption.new.key}")
    private String newKeyString;

    private SecretKey oldKey;
    private SecretKey newKey;

    @PostConstruct
    public void init() throws Exception {
        byte[] decodedKey = Base64.getDecoder().decode(oldKeyString);
        DESKeySpec desKeySpec = new DESKeySpec(decodedKey);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        oldKey = keyFactory.generateSecret(desKeySpec);
        newKey = decodeAesKey(newKeyString);
        log.info("DES to AES key rotation service initialized successfully.");
    }

    private SecretKey decodeAesKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    @Override
    public String rotate(String encryptedData) throws Exception {
        String decrypted = decrypt(encryptedData);
        return AesCipherUtils.aesEncrypt(decrypted, newKey);
    }

    @Override
    public KeyRotationType getType() {
        return KeyRotationType.DES_TO_AES;
    }

    private String decrypt(String encryptedData) throws Exception {
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        Cipher cipher = Cipher.getInstance(DES_ENCRYPT_ALGO);
        cipher.init(Cipher.DECRYPT_MODE, oldKey);
        byte[] decryptedBytes = cipher.doFinal(decodedData);
        return new String(decryptedBytes);
    }
}
