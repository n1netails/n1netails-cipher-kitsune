package com.n1netails.n1netails.cipher.kitsune.factory;

import com.n1netails.n1netails.cipher.kitsune.model.KeyRotationType;
import com.n1netails.n1netails.cipher.kitsune.service.KeyRotationService;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Component
public class KeyRotationServiceFactory {

    private final Map<KeyRotationType, KeyRotationService> keyRotationServiceMap = new EnumMap<>(KeyRotationType.class);

    public KeyRotationServiceFactory(List<KeyRotationService> keyRotationServices) {
        for (KeyRotationService service : keyRotationServices) {
            keyRotationServiceMap.put(service.getType(), service);
        }
    }

    public KeyRotationService getService(KeyRotationType type) {
        return keyRotationServiceMap.get(type);
    }
}
