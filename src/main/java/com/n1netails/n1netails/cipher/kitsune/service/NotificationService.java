package com.n1netails.n1netails.cipher.kitsune.service;

import com.n1netails.n1netails.cipher.kitsune.model.entity.NotificationConfigEntity;
import com.n1netails.n1netails.cipher.kitsune.repository.NotificationConfigRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {

    private final KeyRotationService keyRotationService;
    private final NotificationConfigRepository notificationConfigRepository;

    public void rotateConfigEncryptionValues() {
        log.info("rotating config encrypted values with new key");
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
