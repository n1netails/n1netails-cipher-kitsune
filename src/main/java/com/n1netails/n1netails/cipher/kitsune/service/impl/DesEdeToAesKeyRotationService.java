package com.n1netails.n1netails.cipher.kitsune.service.impl;

import com.n1netails.n1netails.cipher.kitsune.model.KeyRotationType;
import com.n1netails.n1netails.cipher.kitsune.service.KeyRotationService;
import com.n1netails.n1netails.cipher.kitsune.util.AesCipherUtils;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;
import java.util.Base64;

@Slf4j
@Service
public class DesEdeToAesKeyRotationService implements KeyRotationService {

    private static final String DES_EDE_ALGORITHM = "DESede/ECB/PKCS5Padding";
    private static final String AES_ALGORITHM = "AES";

    @Value("${n1netails.encryption.old.key}")
    private String oldKeyString;
    @Value("${n1netails.encryption.new.key}")
    private String newKeyString;

    private SecretKey oldKey;
    private SecretKey newKey;

    @PostConstruct
    public void init() {
        Security.addProvider(new BouncyCastleProvider());
        oldKey = decodeDesEdeKey(oldKeyString);
        newKey = decodeAesKey(newKeyString);
        log.info("3DES to AES key rotation service initialized successfully.");
    }

    private SecretKey decodeDesEdeKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "DESede");
    }

    private SecretKey decodeAesKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, AES_ALGORITHM);
    }

    @Override
    public String rotate(String encryptedData) throws Exception {
        String decrypted = decrypt(encryptedData);
        return AesCipherUtils.aesEncrypt(decrypted, newKey);
    }

    @Override
    public KeyRotationType getType() {
        return KeyRotationType.DES_EDE_TO_AES;
    }

    private String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(DES_EDE_ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, oldKey);
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(decodedData);
        return new String(decryptedBytes);
    }
}
