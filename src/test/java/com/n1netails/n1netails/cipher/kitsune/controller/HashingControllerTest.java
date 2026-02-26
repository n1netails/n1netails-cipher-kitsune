package com.n1netails.n1netails.cipher.kitsune.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n1netails.n1netails.cipher.kitsune.dto.HashRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(properties = {
    "n1netails.database.enabled=false",
    "n1netails.encryption.rotate=false"
})
@AutoConfigureMockMvc
class HashingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testHash() throws Exception {
        HashRequest request = new HashRequest();
        request.setData("n1netails");
        request.setAlgorithm("SHA-256");

        mockMvc.perform(post("/api/v1/hash")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.algorithm").value("SHA-256"))
                .andExpect(jsonPath("$.result").exists());
    }
}
