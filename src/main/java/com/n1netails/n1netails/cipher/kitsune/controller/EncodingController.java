package com.n1netails.n1netails.cipher.kitsune.controller;

import com.n1netails.n1netails.cipher.kitsune.dto.ApiResponse;
import com.n1netails.n1netails.cipher.kitsune.dto.EncodingRequest;
import com.n1netails.n1netails.cipher.kitsune.factory.StrategyFactory;
import com.n1netails.n1netails.cipher.kitsune.strategy.EncodingStrategy;
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
@Tag(name = "Encoding Operations", description = "Endpoints for encoding and decoding data (e.g., Base64, Hex)")
public class EncodingController {

    private final StrategyFactory strategyFactory;

    @PostMapping("/encode")
    @Operation(
            summary = "Encode data using specified algorithm",
            description = "Encodes raw data into a text-based format like Base64 or Hex."
    )
    public ResponseEntity<ApiResponse<String>> encode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Encoding request payload",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Base64 Example",
                                            value = "{\"data\": \"Kitsune Encryption Service\", \"algorithm\": \"Base64\"}"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Hex Example",
                                            value = "{\"data\": \"Kitsune Encryption Service\", \"algorithm\": \"Hex\"}"
                                    )
                            }
                    )
            )
            @Valid @RequestBody EncodingRequest request) throws Exception {
        EncodingStrategy strategy = strategyFactory.resolveEncoding(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.encode(request.getData());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

    @PostMapping("/decode")
    @Operation(
            summary = "Decode data using specified algorithm",
            description = "Decodes text-based data (Base64, Hex) back to its original form."
    )
    public ResponseEntity<ApiResponse<String>> decode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Decoding request payload",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Base64 Example",
                                            value = "{\"data\": \"S2l0c3VuZSBFbmNyeXB0aW9uIFNlcnZpY2U=\", \"algorithm\": \"Base64\"}"
                                    ),
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Hex Example",
                                            value = "{\"data\": \"4b697473756e6520456e6372797074696f6e2053657276696365\", \"algorithm\": \"Hex\"}"
                                    )
                            }
                    )
            )
            @Valid @RequestBody EncodingRequest request) throws Exception {
        EncodingStrategy strategy = strategyFactory.resolveEncoding(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.decode(request.getData());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }
}
