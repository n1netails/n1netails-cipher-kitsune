package com.n1netails.n1netails.cipher.kitsune;

import com.n1netails.n1netails.cipher.kitsune.service.NotificationService;
import com.n1netails.n1netails.cipher.kitsune.util.AesKeyGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class N1netailsCipherServiceApplication implements CommandLineRunner {

	@Value("${n1netails.encryption.rotate}")
	Boolean encryptionRotationEnabled;

	private final NotificationService notificationService;

	public static void main(String[] args) {
		SpringApplication.run(N1netailsCipherServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String AesKey = AesKeyGenerator.genAesKey();
		log.info("--------------------------------");
		log.info("Generated Key: {}", AesKey);
		log.info("--------------------------------");

		if (encryptionRotationEnabled) {
			log.info("===============================================");
			log.info("Starting to rotate encrypted values..");
			log.info("--------------------------------");
			notificationService.rotateConfigEncryptionValues();
		}
	}
}
