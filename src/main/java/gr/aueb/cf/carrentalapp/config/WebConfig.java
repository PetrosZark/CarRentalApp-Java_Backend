package gr.aueb.cf.carrentalapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig class to configure static resource handling in a Spring MVC application.
 * This class maps URLs to files located in a directory outside the project structure,
 * allowing user-uploaded files (like car images) to be served directly.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures resource handlers to serve static files from external directories.
     * Maps requests to /uploads/** to files in the user's home directory under car_rental_uploads.
     *
     * @param registry ResourceHandlerRegistry to register custom resource handlers.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Serve files from the uploads directory outside the project structure
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + System.getProperty("user.home") + "/car_rental_uploads/");
    }
}