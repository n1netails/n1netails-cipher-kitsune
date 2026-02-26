package com.n1netails.n1netails.cipher.kitsune.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n1netails.n1netails.cipher.kitsune.dto.EncodingRequest;
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
class EncodingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testEncodeDecodeFlow() throws Exception {
        EncodingRequest encodeRequest = new EncodingRequest();
        encodeRequest.setData("Kitsune");
        encodeRequest.setAlgorithm("Base64");

        String response = mockMvc.perform(post("/api/v1/encode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(encodeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andReturn().getResponse().getContentAsString();

        String encodedData = objectMapper.readTree(response).get("result").asText();

        EncodingRequest decodeRequest = new EncodingRequest();
        decodeRequest.setData(encodedData);
        decodeRequest.setAlgorithm("Base64");

        mockMvc.perform(post("/api/v1/decode")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(decodeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.result").value("Kitsune"));
    }
}
