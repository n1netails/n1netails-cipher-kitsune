package com.n1netails.n1netails.cipher.kitsune.util;

import java.security.SecureRandom;
import java.util.Base64;

public class JwtSecretGenerator {

    /**
     * Generates a secure random 256-bit JWT secret key, encoded in Base64.
     *
     * @return A Base64 encoded string representing the 256-bit secret key.
     */
    public static String genJwtSecret() {
        byte[] secret = new byte[32]; // 256 bits
        new SecureRandom().nextBytes(secret);
        return Base64.getEncoder().encodeToString(secret);
    }
}
