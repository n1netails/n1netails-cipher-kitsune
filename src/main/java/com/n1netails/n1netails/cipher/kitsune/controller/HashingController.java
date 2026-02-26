package com.n1netails.n1netails.cipher.kitsune.controller;

import com.n1netails.n1netails.cipher.kitsune.dto.ApiResponse;
import com.n1netails.n1netails.cipher.kitsune.dto.HashRequest;
import com.n1netails.n1netails.cipher.kitsune.factory.StrategyFactory;
import com.n1netails.n1netails.cipher.kitsune.strategy.HashingStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Hashing Operations", description = "Endpoints for cryptographic hashing (e.g., SHA-256, Argon2)")
public class HashingController {

    private final StrategyFactory strategyFactory;

    @PostMapping("/hash")
    @Operation(
            summary = "Hash data using specified algorithm",
            description = "Computes a cryptographic hash of the input data. Supports standard algorithms like SHA-256 " +
                    "as well as password hashing algorithms like Argon2 and BCrypt."
    )
    public ResponseEntity<ApiResponse<String>> hash(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Hashing request payload",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "SHA-256 Example",
                                            value = "{\"data\": \"n1netails-kitsune\", \"algorithm\": \"SHA-256\"}"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Argon2 Example",
                                            value = "{\"data\": \"securePassword123\", \"algorithm\": \"Argon2\", \"salt\": \"c2FsdHlfa2l0c3VuZQ==\"}"
                                    )
                            }
                    )
            )
            @Valid @RequestBody HashRequest request) throws Exception {
        HashingStrategy strategy = strategyFactory.resolveHashing(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.hash(request.getData(), request.getSalt());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }
}
