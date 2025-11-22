package com.n1netails.n1netails.cipher.kitsune.service;

public interface KeyRotationService {

    String rotate(String encryptedData) throws Exception;
}
