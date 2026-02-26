package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.HashingStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "n1netails.hashing.bcrypt.enabled", havingValue = "true", matchIfMissing = true)
public class BcryptStrategy implements HashingStrategy {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public String hash(String data, String salt) throws Exception {
        return encoder.encode(data);
    }

    @Override
    public String getAlgorithmName() {
        return "Bcrypt";
    }
}
