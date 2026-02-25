package com.n1netails.n1netails.cipher.kitsune.strategy;

public interface EncryptionStrategy {
    String encrypt(String data, String key) throws Exception;
    String decrypt(String encryptedData, String key) throws Exception;
    String getAlgorithmName();
}
