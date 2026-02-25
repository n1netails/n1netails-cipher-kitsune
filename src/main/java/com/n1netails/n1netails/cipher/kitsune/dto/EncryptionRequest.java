package com.n1netails.n1netails.cipher.kitsune.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EncryptionRequest {
    @NotBlank
    @Size(max = 10000)
    private String data;

    @NotBlank
    private String algorithm;

    private String key;

    private String iv;
}
