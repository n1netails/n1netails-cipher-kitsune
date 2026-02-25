package com.n1netails.n1netails.cipher.kitsune.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request object for encoding and decoding data")
public class EncodingRequest {

    @NotBlank
    @Size(max = 10000)
    @Schema(description = "Data to be encoded or decoded", example = "Kitsune Encryption Service")
    private String data;

    @NotBlank
    @Schema(description = "Encoding algorithm to use (e.g., Base64, Hex)", example = "Base64")
    private String algorithm;
}
