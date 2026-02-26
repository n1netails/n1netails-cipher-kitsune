package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.HashingStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "n1netails.hashing.argon2.enabled", havingValue = "true", matchIfMissing = true)
public class Argon2Strategy implements HashingStrategy {

    private final Argon2PasswordEncoder encoder = Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();

    @Override
    public String hash(String data, String salt) throws Exception {
        return encoder.encode(data);
    }

    @Override
    public String getAlgorithmName() {
        return "Argon2";
    }
}
