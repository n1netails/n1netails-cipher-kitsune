package com.n1netails.n1netails.cipher.kitsune.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiOAuthProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springdoc.webmvc.ui.SwaggerIndexPageTransformer;
import org.springdoc.webmvc.ui.SwaggerIndexTransformer;
import org.springdoc.webmvc.ui.SwaggerWelcomeCommon;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.resource.ResourceTransformerChain;
import org.springframework.web.servlet.resource.TransformedResource;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Configuration
public class SwaggerConfig {

    @Bean
    @Primary
    public SwaggerIndexTransformer swaggerIndexTransformer(
            SwaggerUiConfigProperties swaggerUiConfigProperties,
            SwaggerUiOAuthProperties swaggerUiOAuthProperties,
            SwaggerWelcomeCommon swaggerWelcomeCommon,
            ObjectMapperProvider objectMapperProvider) {
        return new SwaggerIndexPageTransformer(swaggerUiConfigProperties, swaggerUiOAuthProperties, swaggerWelcomeCommon, objectMapperProvider) {
            @Override
            public Resource transform(HttpServletRequest request, Resource resource, ResourceTransformerChain transformerChain) throws IOException {
                if (resource.getFilename() != null && resource.getFilename().equals("index.html")) {
                    String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
                    content = content.replace("</head>", "<link rel=\"stylesheet\" type=\"text/css\" href=\"/css/custom.css\"></head>");
                    return new TransformedResource(resource, content.getBytes(StandardCharsets.UTF_8));
                }
                return super.transform(request, resource, transformerChain);
            }
        };
    }

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
                                "This service does **not** log sensitive data. All encryption keys provided by the user are used only for the duration of the request.\n\n" +
                                "### 🚀 Getting Started\n" +
                                "1. Use `/generate/key` to create a symmetric key.\n" +
                                "2. Use `/encrypt` with the generated key and data.\n" +
                                "3. Copy the result and use it in `/decrypt` to retrieve your original data.")
                        .contact(new Contact().name("N1netails Support").url("https://discord.gg/ma9CCw7F2x"))
                        .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")));
    }
}
