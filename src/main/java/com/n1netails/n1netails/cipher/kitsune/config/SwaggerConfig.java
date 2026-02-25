package com.n1netails.n1netails.cipher.kitsune.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("N1netails Cipher Kitsune API")
                        .version("v0.0.5")
                        .description("Production-ready **Encryption-as-a-Service (EaaS)** platform.\n\n" +
                                "### 🎯 Features\n" +
                                "- Symmetric & Asymmetric Encryption\n" +
                                "- Secure Hashing (Argon2, BCrypt)\n" +
                                "- Key & IV Generation\n" +
                                "- Rate Limiting & Security Hardened\n\n" +
                                "### 🔐 Security Note\n" +
                                "This service does **not** log sensitive data. All encryption keys provided by the user are used only for the duration of the request.")
                        .contact(new Contact().name("N1netails Support").url("https://discord.gg/ma9CCw7F2x"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
