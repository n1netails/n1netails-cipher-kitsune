package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.HashingStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.util.Base64;

@Component
@ConditionalOnProperty(name = "n1netails.hashing.sha-256.enabled", havingValue = "true", matchIfMissing = true)
public class Sha256Strategy implements HashingStrategy {

    @Override
    public String hash(String data, String salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        if (salt != null && !salt.isEmpty()) {
            digest.update(salt.getBytes());
        }
        byte[] hash = digest.digest(data.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    @Override
    public String getAlgorithmName() {
        return "SHA-256";
    }
}
