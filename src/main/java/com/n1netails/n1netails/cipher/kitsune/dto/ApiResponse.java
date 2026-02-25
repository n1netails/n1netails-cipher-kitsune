package com.n1netails.n1netails.cipher.kitsune.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String algorithm;
    private T result;
    private String message;
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
