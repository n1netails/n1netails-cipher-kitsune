package com.n1netails.n1netails.cipher.kitsune;

import com.n1netails.n1netails.cipher.kitsune.service.NotificationService;
import com.n1netails.n1netails.cipher.kitsune.util.AesKeyGenerator;
import com.n1netails.n1netails.cipher.kitsune.util.JwtSecretGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@Slf4j
@SpringBootApplication
public class N1netailsCipherServiceApplication implements CommandLineRunner {

	@Value("${n1netails.encryption.rotate}")
	Boolean encryptionRotationEnabled;

	@Autowired(required = false)
	private NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(N1netailsCipherServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String AesKey = AesKeyGenerator.genAesKey();
		String JwtSecret = JwtSecretGenerator.genJwtSecret();
		log.info("--------------------------------");
		log.info("Generated AES Key: {}", AesKey);
		log.info("Generated JWT Secret: {}", JwtSecret);
		log.info("--------------------------------");

		if (encryptionRotationEnabled) {
			log.info("===============================================");
			log.info("Starting to rotate encrypted values..");
			log.info("--------------------------------");
			Optional.ofNullable(notificationService).ifPresent(NotificationService::rotateConfigEncryptionValues);
		}
	}
}
