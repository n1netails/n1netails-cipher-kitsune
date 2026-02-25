package com.n1netails.n1netails.cipher.kitsune.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request object for hashing data")
public class HashRequest {

    @NotBlank
    @Size(max = 10000)
    @Schema(description = "Data to be hashed", example = "n1netails-kitsune")
    private String data;

    @NotBlank
    @Schema(description = "Hashing algorithm to use (e.g., SHA-256, Argon2, BCrypt)", example = "SHA-256")
    private String algorithm;

    @Schema(description = "Optional salt for hashing (Base64 encoded recommended for binary salts)", example = "c2FsdHlfa2l0c3VuZQ==")
    private String salt;
}
