package com.n1netails.n1netails.cipher.kitsune.service;

import com.n1netails.n1netails.cipher.kitsune.util.*;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@Service
public class KeyGenerationService {

    public String generateSymmetricKey(String algorithm) throws Exception {
        return switch (algorithm.toUpperCase().replace("-", "")) {
            case "AES" -> AesKeyGenerator.genAesKey();
            case "CHACHA20" -> ChaCha20KeyGenerator.genChaCha20Key();
            case "TWOFISH" -> TwofishKeyGenerator.genTwofishKey();
            case "JWT" -> JwtSecretGenerator.genJwtSecret();
            default -> throw new IllegalArgumentException("Unsupported algorithm for symmetric key generation: " + algorithm);
        };
    }

    public Map<String, String> generateAsymmetricKeyPair(String algorithm) throws Exception {
        return switch (algorithm.toUpperCase().replace("-", "")) {
            case "RSA" -> AsymmetricKeyGenerator.generateRsaKeyPair(2048);
            case "EC" -> AsymmetricKeyGenerator.generateEcKeyPair("secp256r1");
            default -> throw new IllegalArgumentException("Unsupported algorithm for asymmetric key generation: " + algorithm);
        };
    }

    public String generateIv(int length) {
        byte[] iv = new byte[length];
        new SecureRandom().nextBytes(iv);
        return Base64.getEncoder().encodeToString(iv);
    }
}
