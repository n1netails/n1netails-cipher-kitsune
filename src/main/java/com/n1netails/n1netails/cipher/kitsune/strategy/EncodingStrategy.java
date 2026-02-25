package com.n1netails.n1netails.cipher.kitsune.strategy;

public interface EncodingStrategy {
    String encode(String data) throws Exception;
    String decode(String encodedData) throws Exception;
    String getAlgorithmName();
}
