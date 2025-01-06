package gr.aueb.cf.carrentalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main entry point for the Car Rental Application.
 * <p>
 * This class bootstraps the Spring Boot application by initializing all components,
 * auto-configuring beans, and starting the embedded Tomcat server.
 * </p>
 */
@SpringBootApplication  // Marks this class as the primary configuration class and enables component scanning
@EnableJpaAuditing  // Enables auditing for JPA entities (e.g., @CreatedDate, @LastModifiedDate)
public class CarRentalApplication {

	/**
	 * Main method that launches the Spring Boot application.
	 * <p>
	 * It delegates to Spring Boot’s {@link SpringApplication} class, which handles
	 * application lifecycle management, dependency injection, and component initialization.
	 * </p>
	 *
	 * @param args Command-line arguments passed during application startup
	 */
	public static void main(String[] args) {
		SpringApplication.run(CarRentalApplication.class, args);
	}
}
