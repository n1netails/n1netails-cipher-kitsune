package com.n1netails.n1netails.cipher.kitsune.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.n1netails.n1netails.cipher.kitsune.repository")
@ConditionalOnProperty(name = "n1netails.database.enabled", havingValue = "true", matchIfMissing = true)
public class JpaConfig {
}
