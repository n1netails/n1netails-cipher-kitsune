package com.n1netails.n1netails.cipher.kitsune.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Universal API response wrapper")
public class ApiResponse<T> {

    @Schema(description = "Indicates if the operation was successful", example = "true")
    private boolean success;

    @Schema(description = "The algorithm used for the operation", example = "AES-GCM")
    private String algorithm;

    @Schema(description = "The result of the operation (e.g., ciphertext, hash, or generated key)")
    private T result;

    @Schema(description = "Optional status message, typically used for errors", example = "Operation completed successfully")
    private String message;

    @Schema(description = "Timestamp of the response", example = "2023-10-27T10:00:00")
    private LocalDateTime timestamp;

    public static <T> ApiResponse<T> success(T result, String algorithm) {
        return ApiResponse.<T>builder()
                .success(true)
                .result(result)
                .algorithm(algorithm)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
