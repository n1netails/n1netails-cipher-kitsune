package com.n1netails.n1netails.cipher.kitsune.strategy.impl;

import com.n1netails.n1netails.cipher.kitsune.strategy.EncodingStrategy;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "n1netails.encoding.hex.enabled", havingValue = "true", matchIfMissing = true)
public class HexStrategy implements EncodingStrategy {

    @Override
    public String encode(String data) throws Exception {
        return Hex.toHexString(data.getBytes());
    }

    @Override
    public String decode(String encodedData) throws Exception {
        return new String(Hex.decode(encodedData));
    }

    @Override
    public String getAlgorithmName() {
        return "Hex";
    }
}
