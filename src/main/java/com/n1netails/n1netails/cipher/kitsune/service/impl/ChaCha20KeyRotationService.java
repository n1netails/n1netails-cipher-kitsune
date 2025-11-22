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
public class ChaCha20KeyRotationService implements KeyRotationService {

    private static final String ALGORITHM = "ChaCha20";

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
        log.info("ChaCha20 key rotation service initialized successfully.");
    }

    private SecretKey decodeKey(String base64Key) {
        byte[] decodedKey = Base64.getDecoder().decode(base64Key);
        return new SecretKeySpec(decodedKey, "ChaCha20");
    }

    @Override
    public String rotate(String encryptedData) throws Exception {
        String decrypted = decrypt(encryptedData);
        return encrypt(decrypted);
    }

    @Override
    public KeyRotationType getType() {
        return KeyRotationType.CHACHA20;
    }

    private String encrypt(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        byte[] nonce = new byte[12];
        new java.security.SecureRandom().nextBytes(nonce);
        cipher.init(Cipher.ENCRYPT_MODE, newKey, new IvParameterSpec(nonce));
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        byte[] combined = new byte[nonce.length + encryptedBytes.length];
        System.arraycopy(nonce, 0, combined, 0, nonce.length);
        System.arraycopy(encryptedBytes, 0, combined, nonce.length, encryptedBytes.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    private String decrypt(String encryptedData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM, "BC");
        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] nonce = new byte[12];
        System.arraycopy(decodedData, 0, nonce, 0, nonce.length);
        cipher.init(Cipher.DECRYPT_MODE, oldKey, new IvParameterSpec(nonce));
        byte[] encryptedBytes = new byte[decodedData.length - nonce.length];
        System.arraycopy(decodedData, nonce.length, encryptedBytes, 0, encryptedBytes.length);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }
}
