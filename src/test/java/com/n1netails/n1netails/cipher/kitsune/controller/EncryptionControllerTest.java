package com.n1netails.n1netails.cipher.kitsune.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n1netails.n1netails.cipher.kitsune.dto.EncryptionRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {
    "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration",
    "n1netails.database.enabled=false",
    "n1netails.encryption.rotate=false"
})
@AutoConfigureMockMvc
class EncryptionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEncryptDecryptFlow() throws Exception {
        String key = Base64.getEncoder().encodeToString(new byte[32]);
        EncryptionRequest encryptRequest = new EncryptionRequest();
        encryptRequest.setData("SecretData");
        encryptRequest.setAlgorithm("AES-GCM");
        encryptRequest.setKey(key);

        String response = mockMvc.perform(post("/api/v1/encrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(encryptRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse().getContentAsString();

        String encryptedData = objectMapper.readTree(response).get("result").asText();

        EncryptionRequest decryptRequest = new EncryptionRequest();
        decryptRequest.setData(encryptedData);
        decryptRequest.setAlgorithm("AES-GCM");
        decryptRequest.setKey(key);

        mockMvc.perform(post("/api/v1/decrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(decryptRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value("SecretData"));
    }

    @Test
    void testInvalidAlgorithm() throws Exception {
        EncryptionRequest request = new EncryptionRequest();
        request.setData("data");
        request.setAlgorithm("INVALID");

        mockMvc.perform(post("/api/v1/encrypt")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.success").value(false));
    }
}
