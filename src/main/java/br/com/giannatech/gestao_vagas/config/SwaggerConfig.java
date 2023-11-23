package br.com.giannatech.gestao_vagas.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

  public OpenAPI openAPI() {
    return new OpenAPI()
        .info(
            new Info()
                .title("Gestào de Vagas API")
                .description("API responsável por gerenciar vagas de emprego")
                .version("1.0"))
        .schemaRequirement("jwt_auth", securityScheme());
  }

  private SecurityScheme securityScheme() {
    return new SecurityScheme()
        .name("jwt_auth")
        .description("JWT token")
        .scheme("bearer")
        .bearerFormat("JWT")
        .type(SecurityScheme.Type.HTTP);
  }
}