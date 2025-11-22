package com.n1netails.n1netails.cipher.kitsune.service;

import com.n1netails.n1netails.cipher.kitsune.factory.KeyRotationServiceFactory;
import com.n1netails.n1netails.cipher.kitsune.model.KeyRotationType;
import com.n1netails.n1netails.cipher.kitsune.model.entity.NotificationConfigEntity;
import com.n1netails.n1netails.cipher.kitsune.repository.NotificationConfigRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@ConditionalOnProperty(name = "n1netails.database.enabled", havingValue = "true", matchIfMissing = true)
public class NotificationService {

    @Value("${n1netails.encryption.key-rotation-type}")
    private KeyRotationType keyRotationType;

    private final KeyRotationServiceFactory keyRotationServiceFactory;
    private final NotificationConfigRepository notificationConfigRepository;

    @Autowired
    public NotificationService(KeyRotationServiceFactory keyRotationServiceFactory, Optional<NotificationConfigRepository> notificationConfigRepository) {
        this.keyRotationServiceFactory = keyRotationServiceFactory;
        this.notificationConfigRepository = notificationConfigRepository.orElse(null);
    }


    public void rotateConfigEncryptionValues() {
        if (notificationConfigRepository == null) {
            log.warn("Database is disabled. Skipping encryption value rotation.");
            return;
        }

        log.info("rotating config encrypted values with new key");
        KeyRotationService keyRotationService = keyRotationServiceFactory.getService(keyRotationType);
        List<NotificationConfigEntity> configEntities = notificationConfigRepository.findAll();
        configEntities.forEach(config -> {
            log.info("starting rotation for token: {}", config.getTokenId());
            log.info("starting rotation for platform: {}", config.getPlatform());
            Map<String, String> details = config.getDetails();
            details.forEach((key, value) -> {
                log.info("rotating: {}", key);
                try {
                    String rotatedValue = keyRotationService.rotate(value);
                    details.put(key, rotatedValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
            config.setDetails(details);
            notificationConfigRepository.save(config);
        });
    }
}
