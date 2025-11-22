package com.n1netails.n1netails.cipher.kitsune.service.impl;

import com.n1netails.n1netails.cipher.kitsune.model.KeyRotationType;
import com.n1netails.n1netails.cipher.kitsune.service.KeyRotationService;
import com.n1netails.n1netails.cipher.kitsune.util.AesCipherUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Slf4j
@Service
public class AesKeyRotationService implements KeyRotationService {

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
        log.info("AES to AES key rotation service initialized successfully.");
    }

    private SecretKey decodeKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    @Override
    public String rotate(String encryptedData) throws Exception {
        String decrypted = AesCipherUtils.aesDecrypt(encryptedData, oldKey);
        return AesCipherUtils.aesEncrypt(decrypted, newKey);
    }

    @Override
    public KeyRotationType getType() {
        return KeyRotationType.AES_TO_AES;
    }
}
