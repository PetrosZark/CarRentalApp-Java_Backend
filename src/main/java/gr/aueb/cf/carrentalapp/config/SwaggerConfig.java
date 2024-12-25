package gr.aueb.cf.carrentalapp.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger Configuration for the Car Rental API.
 * This class configures OpenAPI 3.0 for automatic API documentation generation using Swagger.
 * It defines API metadata (title, version, description) and configures global JWT authentication.
 */
@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Car Rental API",
                version = "1.0",
                description = "API documentation for the Car Rental application"
        ),
        // Apply JWT globally
        security = @SecurityRequirement(name = "Bearer Authentication")
)
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)

public class SwaggerConfig {
        // This class doesn't need additional methods or logic.
        // The annotations are sufficient to configure Swagger globally.
}

