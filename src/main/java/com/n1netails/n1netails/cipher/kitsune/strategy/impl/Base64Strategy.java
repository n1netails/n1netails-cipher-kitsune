package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.EncodingStrategy;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@ConditionalOnProperty(name = "n1netails.encoding.base64.enabled", havingValue = "true", matchIfMissing = true)
public class Base64Strategy implements EncodingStrategy {

    @Override
    public String encode(String data) throws Exception {
        return Base64.getEncoder().encodeToString(data.getBytes());
    }

    @Override
    public String decode(String encodedData) throws Exception {
        return new String(Base64.getDecoder().decode(encodedData));
    }

    @Override
    public String getAlgorithmName() {
        return "Base64";
    }
}
