package com.n1netails.n1netails.cipher.kitsune.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "n1netails")
public class EncryptionProperties {
    private RateLimitConfig ratelimit = new RateLimitConfig();
    private EncryptionConfig encryption = new EncryptionConfig();
    private HashingConfig hashing = new HashingConfig();

    @Data
    public static class RateLimitConfig {
        private int capacity = 100;
        private int tokensPerSecond = 10;
    }

    @Data
    public static class EncryptionConfig {
        private AlgorithmConfig aesGcm = new AlgorithmConfig();
        private AlgorithmConfig chacha20 = new AlgorithmConfig();
        private AlgorithmConfig rsaOaep = new AlgorithmConfig();
        private AlgorithmConfig ec = new AlgorithmConfig();
    }

    @Data
    public static class HashingConfig {
        private AlgorithmConfig sha256 = new AlgorithmConfig();
        private AlgorithmConfig sha512 = new AlgorithmConfig();
        private AlgorithmConfig argon2 = new AlgorithmConfig();
        private AlgorithmConfig bcrypt = new AlgorithmConfig();
    }

    @Data
    public static class AlgorithmConfig {
        private boolean enabled = true;
    }
}
