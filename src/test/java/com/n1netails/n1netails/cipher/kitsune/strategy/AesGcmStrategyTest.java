package com.n1netails.n1netails.cipher.kitsune.strategy;

import com.n1netails.n1netails.cipher.kitsune.strategy.impl.AesGcmStrategy;
import org.junit.jupiter.api.Test;
import java.util.Base64;
import static org.junit.jupiter.api.Assertions.*;

class AesGcmStrategyTest {
    private final AesGcmStrategy strategy = new AesGcmStrategy();
    private final String key = Base64.getEncoder().encodeToString(new byte[32]);

    @Test
    void testEncryptDecrypt() throws Exception {
        String data = "Hello, World!";
        String encrypted = strategy.encrypt(data, key);
        assertNotNull(encrypted);
        String decrypted = strategy.decrypt(encrypted, key);
        assertEquals(data, decrypted);
    }
}
