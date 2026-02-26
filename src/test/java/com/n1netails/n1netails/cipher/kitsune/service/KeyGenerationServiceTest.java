package com.n1netails.n1netails.cipher.kitsune.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration",
    "n1netails.database.enabled=false"
})
class KeyGenerationServiceTest {

    @Autowired
    private KeyGenerationService keyGenerationService;

    @Test
    void testGenerateSymmetricKey() throws Exception {
        String aesKey = keyGenerationService.generateSymmetricKey("AES");
        assertNotNull(aesKey);

        String chachaKey = keyGenerationService.generateSymmetricKey("CHACHA20");
        assertNotNull(chachaKey);
    }

    @Test
    void testGenerateAsymmetricKeyPair() throws Exception {
        Map<String, String> rsaPair = keyGenerationService.generateAsymmetricKeyPair("RSA");
        assertNotNull(rsaPair.get("publicKey"));
        assertNotNull(rsaPair.get("privateKey"));

        Map<String, String> ecPair = keyGenerationService.generateAsymmetricKeyPair("EC");
        assertNotNull(ecPair.get("publicKey"));
        assertNotNull(ecPair.get("privateKey"));
    }

    @Test
    void testGenerateIv() {
        String iv = keyGenerationService.generateIv(12);
        assertNotNull(iv);
    }
}
