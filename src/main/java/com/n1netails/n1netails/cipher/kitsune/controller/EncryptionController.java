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
    @Operation(
            summary = "Encrypt data using specified algorithm",
            description = "Encrypts the provided plaintext using the specified symmetric or asymmetric algorithm. " +
                    "For AES-GCM, a 128-bit or 256-bit key (Base64) and a 12-byte IV (Base64) are recommended. " +
                    "The output format for AES-GCM is IV + Ciphertext (Base64 encoded).",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Data encrypted successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"success\": true, \"algorithm\": \"AES-GCM\", \"result\": \"MTIzNDU2Nzg5MDEywjO9gNMWVX4A+mKXY5sj3vOZmhNfmVt/dRfgcwHHOSqYVOuVF9A=\", \"timestamp\": \"2023-10-27T10:00:00\"}"
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<String>> encrypt(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Encryption request payload",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "AES-GCM Example",
                                    value = "{\"data\": \"Kitsune Secret Message\", \"algorithm\": \"AES\", \"key\": \"MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=\", \"iv\": \"MTIzNDU2Nzg5MDEy\"}"
                            )
                    )
            )
            @Valid @RequestBody EncryptionRequest request) throws Exception {
        EncryptionStrategy strategy = strategyFactory.resolveEncryption(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.encrypt(request.getData(), request.getKey());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

    @PostMapping("/decrypt")
    @Operation(
            summary = "Decrypt data using specified algorithm",
            description = "Decrypts the provided ciphertext using the specified algorithm and key. " +
                    "For algorithms like AES-GCM, the IV must be prepended to the ciphertext or provided in the 'iv' field depending on the implementation."
    )
    public ResponseEntity<ApiResponse<String>> decrypt(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Decryption request payload",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "AES-GCM Example",
                                    value = "{\"data\": \"MTIzNDU2Nzg5MDEywjO9gNMWVX4A+mKXY5sj3vOZmhNfmVt/dRfgcwHHOSqYVOuVF9A=\", \"algorithm\": \"AES\", \"key\": \"MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=\"}"
                            )
                    )
            )
            @Valid @RequestBody EncryptionRequest request) throws Exception {
        EncryptionStrategy strategy = strategyFactory.resolveEncryption(request.getAlgorithm());
        if (strategy == null) {
            return ResponseEntity.status(403).body(ApiResponse.error("Algorithm disabled or not found"));
        }
        String result = strategy.decrypt(request.getData(), request.getKey());
        return ResponseEntity.ok(ApiResponse.success(result, strategy.getAlgorithmName()));
    }

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
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "SHA-256 Example",
                                    value = "{\"data\": \"n1netails-kitsune\", \"algorithm\": \"SHA-256\"}"
                            )
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

    @PostMapping("/encode")
    @Operation(
            summary = "Encode data using specified algorithm",
            description = "Encodes raw data into a text-based format like Base64 or Hex."
    )
    public ResponseEntity<ApiResponse<String>> encode(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Encoding request payload",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Base64 Example",
                                    value = "{\"data\": \"Kitsune Encryption Service\", \"algorithm\": \"Base64\"}"
                            )
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
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Base64 Example",
                                    value = "{\"data\": \"S2l0c3VuZSBFbmNyeXB0aW9uIFNlcnZpY2U=\", \"algorithm\": \"Base64\"}"
                            )
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

    @GetMapping("/generate/key/{algorithm}")
    @Operation(
            summary = "Generate a new symmetric key for the specified algorithm",
            description = "Generates a cryptographically strong symmetric key for algorithms like AES or ChaCha20. Result is Base64 encoded.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Key generated successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"success\": true, \"algorithm\": \"AES\", \"result\": \"MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=\", \"timestamp\": \"2023-10-27T10:00:00\"}"
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<String>> generateKey(
            @io.swagger.v3.oas.annotations.Parameter(description = "Symmetric algorithm (e.g., AES, ChaCha20)", example = "AES")
            @PathVariable String algorithm) throws Exception {
        String key = keyGenerationService.generateSymmetricKey(algorithm);
        return ResponseEntity.ok(ApiResponse.success(key, algorithm.toUpperCase()));
    }

    @GetMapping("/generate/keypair/{algorithm}")
    @Operation(
            summary = "Generate a new asymmetric key pair for the specified algorithm",
            description = "Generates a public/private key pair for asymmetric algorithms like RSA or EC. Keys are returned in PEM format.",
            responses = @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Key pair generated successfully",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    value = "{\"success\": true, \"algorithm\": \"RSA\", \"result\": {\"publicKey\": \"-----BEGIN PUBLIC KEY-----\\nMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzLIDOOhiqNlIVGCRAbyo...\\n-----END PUBLIC KEY-----\", \"privateKey\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDMsgM46GKo2UhU...\\n-----END PRIVATE KEY-----\"}, \"timestamp\": \"2023-10-27T10:00:00\"}"
                            )
                    )
            )
    )
    public ResponseEntity<ApiResponse<Map<String, String>>> generateKeyPair(
            @io.swagger.v3.oas.annotations.Parameter(description = "Asymmetric algorithm (e.g., RSA, EC)", example = "RSA")
            @PathVariable String algorithm) throws Exception {
        Map<String, String> keyPair = keyGenerationService.generateAsymmetricKeyPair(algorithm);
        return ResponseEntity.ok(ApiResponse.success(keyPair, algorithm.toUpperCase()));
    }

    @GetMapping("/generate/iv")
    @Operation(
            summary = "Generate a new initialization vector (IV)",
            description = "Generates a cryptographically strong random initialization vector (IV). Result is Base64 encoded."
    )
    public ResponseEntity<ApiResponse<String>> generateIv(
            @io.swagger.v3.oas.annotations.Parameter(description = "Length of the IV in bytes", example = "12")
            @RequestParam(defaultValue = "12") int length) {
        String iv = keyGenerationService.generateIv(length);
        return ResponseEntity.ok(ApiResponse.success(iv, "SECURE-RANDOM"));
    }
}
