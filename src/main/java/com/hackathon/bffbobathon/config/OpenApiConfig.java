package com.hackathon.bffbobathon.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do OpenAPI (Swagger) para documentação da API.
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BFF Bob-a-thon API")
                        .version("1.0.0")
                        .description("API Backend for Frontend para gerenciamento de conteúdos educacionais do Bob-a-thon")
                        .contact(new Contact()
                                .name("Equipe Bob-a-thon")
                                .email("bob-a-thon@hackathon.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}

// Made with Bob
