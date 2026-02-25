package com.n1netails.n1netails.cipher.kitsune.controller;

import com.n1netails.n1netails.cipher.kitsune.dto.*;
import com.n1netails.n1netails.cipher.kitsune.factory.StrategyFactory;
import com.n1netails.n1netails.cipher.kitsune.service.KeyGenerationService;
import com.n1netails.n1netails.cipher.kitsune.strategy.EncodingStrategy;
import com.n1netails.n1netails.cipher.kitsune.strategy.EncryptionStrategy;
import com.n1netails.n1netails.cipher.kitsune.strategy.HashingStrategy;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Encryption Service", description = "Endpoints for encryption, hashing, and key generation")
public class EncryptionController {

    private final StrategyFactory strategyFactory;
    private final KeyGenerationService keyGenerationService;

    @PostMapping("/encrypt")
    @Operation(summary = "Encrypt data using specified algorithm")
    public ResponseEntity<ApiResponse<String>> encrypt(@Valid @RequestBody EncryptionRequest request) throws Exception {
        EncryptionStrategy strategy = strategyFactory.resolveEncryption(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.encrypt(request.getData(), request.getKey());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

    @PostMapping("/decrypt")
    @Operation(summary = "Decrypt data using specified algorithm")
    public ResponseEntity<ApiResponse<String>> decrypt(@Valid @RequestBody EncryptionRequest request) throws Exception {
        EncryptionStrategy strategy = strategyFactory.resolveEncryption(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.decrypt(request.getData(), request.getKey());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

    @PostMapping("/hash")
    @Operation(summary = "Hash data using specified algorithm")
    public ResponseEntity<ApiResponse<String>> hash(@Valid @RequestBody HashRequest request) throws Exception {
        HashingStrategy strategy = strategyFactory.resolveHashing(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.hash(request.getData(), request.getSalt());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

    @PostMapping("/encode")
    @Operation(summary = "Encode data using specified algorithm")
    public ResponseEntity<ApiResponse<String>> encode(@Valid @RequestBody EncodingRequest request) throws Exception {
        EncodingStrategy strategy = strategyFactory.resolveEncoding(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.encode(request.getData());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

    @PostMapping("/decode")
    @Operation(summary = "Decode data using specified algorithm")
    public ResponseEntity<ApiResponse<String>> decode(@Valid @RequestBody EncodingRequest request) throws Exception {
        EncodingStrategy strategy = strategyFactory.resolveEncoding(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.decode(request.getData());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

    @GetMapping("/generate/key/{algorithm}")
    @Operation(summary = "Generate a new symmetric key for the specified algorithm")
    public ResponseEntity<ApiResponse<String>> generateKey(@PathVariable String algorithm) throws Exception {
        String key = keyGenerationService.generateSymmetricKey(algorithm);
        return ResponseEntity.ok(ApiResponse.success(key, algorithm.toUpperCase()));
    }

    @GetMapping("/generate/keypair/{algorithm}")
    @Operation(summary = "Generate a new asymmetric key pair for the specified algorithm")
    public ResponseEntity<ApiResponse<Map<String, String>>> generateKeyPair(@PathVariable String algorithm) throws Exception {
        Map<String, String> keyPair = keyGenerationService.generateAsymmetricKeyPair(algorithm);
        return ResponseEntity.ok(ApiResponse.success(keyPair, algorithm.toUpperCase()));
    }

    @GetMapping("/generate/iv")
    @Operation(summary = "Generate a new initialization vector (IV)")
    public ResponseEntity<ApiResponse<String>> generateIv(@RequestParam(defaultValue = "12") int length) {
        String iv = keyGenerationService.generateIv(length);
        return ResponseEntity.ok(ApiResponse.success(iv, "SECURE-RANDOM"));
    }
}
