package com.n1netails.n1netails.cipher.kitsune.service;

import com.n1netails.n1netails.cipher.kitsune.model.KeyRotationType;

public interface KeyRotationService {

    String rotate(String encryptedData) throws Exception;

    KeyRotationType getType();
}
