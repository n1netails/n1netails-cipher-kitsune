package com.n1netails.n1netails.cipher.kitsune.factory;

import com.n1netails.n1netails.cipher.kitsune.strategy.EncodingStrategy;
import com.n1netails.n1netails.cipher.kitsune.strategy.EncryptionStrategy;
import com.n1netails.n1netails.cipher.kitsune.strategy.HashingStrategy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StrategyFactory {

    private final Map<String, EncryptionStrategy> encryptionStrategies;
    private final Map<String, HashingStrategy> hashingStrategies;
    private final Map<String, EncodingStrategy> encodingStrategies;

    public StrategyFactory(List<EncryptionStrategy> encryptionStrategies,
                           List<HashingStrategy> hashingStrategies,
                           List<EncodingStrategy> encodingStrategies) {
        this.encryptionStrategies = encryptionStrategies.stream()
                .collect(Collectors.toMap(s -> s.getAlgorithmName().toUpperCase(), s -> s));
        this.hashingStrategies = hashingStrategies.stream()
                .collect(Collectors.toMap(s -> s.getAlgorithmName().toUpperCase(), s -> s));
        this.encodingStrategies = encodingStrategies.stream()
                .collect(Collectors.toMap(s -> s.getAlgorithmName().toUpperCase(), s -> s));
    }

    public EncryptionStrategy getEncryptionStrategy(String algorithm) {
        return encryptionStrategies.get(algorithm.toUpperCase().replace("-", ""));
    }

    public EncryptionStrategy getEncryptionStrategyByStrictName(String algorithm) {
        // Try exact match, then try removing hyphens
        EncryptionStrategy strategy = encryptionStrategies.get(algorithm.toUpperCase());
        if (strategy == null) {
            strategy = encryptionStrategies.get(algorithm.toUpperCase().replace("-", ""));
        }
        if (strategy == null) {
            // Special cases
            if (algorithm.equalsIgnoreCase("AES-GCM")) return encryptionStrategies.get("AES-GCM");
            if (algorithm.equalsIgnoreCase("CHACHA20-POLY1305")) return encryptionStrategies.get("CHACHA20-POLY1305");
        }
        return strategy;
    }

    // Better implementation of strategy resolution
    public EncryptionStrategy resolveEncryption(String algorithm) {
        return findInMap(encryptionStrategies, algorithm);
    }

    public HashingStrategy resolveHashing(String algorithm) {
        return findInMap(hashingStrategies, algorithm);
    }

    public EncodingStrategy resolveEncoding(String algorithm) {
        return findInMap(encodingStrategies, algorithm);
    }

    private <T> T findInMap(Map<String, T> map, String algorithm) {
        String key = algorithm.toUpperCase();
        if (map.containsKey(key)) return map.get(key);

        // Try normalized key (no hyphens, no underscores)
        String normalizedKey = key.replace("-", "").replace("_", "");
        for (Map.Entry<String, T> entry : map.entrySet()) {
            if (entry.getKey().replace("-", "").replace("_", "").equals(normalizedKey)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
