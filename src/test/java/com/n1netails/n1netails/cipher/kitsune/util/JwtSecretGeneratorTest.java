package com.n1netails.n1netails.cipher.kitsune.util;

import org.junit.jupiter.api.Test;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

class JwtSecretGeneratorTest {

    @Test
    void testGenJwtSecret() {
        String secret = JwtSecretGenerator.genJwtSecret();
        assertNotNull(secret);
        assertFalse(secret.isEmpty());

        byte[] decoded = Base64.getDecoder().decode(secret);
        assertEquals(32, decoded.length, "Secret should be 256 bits (32 bytes)");
    }

    @Test
    void testGenJwtSecretUniqueness() {
        String secret1 = JwtSecretGenerator.genJwtSecret();
        String secret2 = JwtSecretGenerator.genJwtSecret();
        assertNotEquals(secret1, secret2, "Generated secrets should be unique");
    }
}
