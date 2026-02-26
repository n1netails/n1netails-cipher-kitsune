package com.n1netails.n1netails.cipher.kitsune.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request object for symmetric and asymmetric encryption/decryption")
public class EncryptionRequest {

    @NotBlank
    @Size(max = 10000)
    @Schema(description = "The data to encrypt or decrypt", example = "Kitsune Secret Message")
    private String data;

    @NotBlank
    @Schema(description = "The algorithm to use (e.g., AES, RSA-OAEP, CHACHA20)", example = "AES")
    private String algorithm;

    @Schema(description = "The encryption key (Base64 encoded for symmetric, PEM for asymmetric)",
            example = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=")
    private String key;

    @Schema(description = "Initialization Vector (Base64 encoded), required for certain algorithms like AES-GCM",
            example = "MTIzNDU2Nzg5MDEy")
    private String iv;
}
