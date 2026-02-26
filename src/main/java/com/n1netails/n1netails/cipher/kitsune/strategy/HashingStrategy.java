package com.n1netails.n1netails.cipher.kitsune.strategy;

public interface HashingStrategy {
    String hash(String data, String salt) throws Exception;
    String getAlgorithmName();
}
