package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing a read-only view of a car.
 * This class is used to return car details to the client while keeping certain fields immutable.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarReadOnlyDTO {

    /**
     * The unique identifier of the car (as a string to accommodate UUIDs or custom formats).
     */
    private String id;

    /**
     * The manufacturing year of the car.
     */
    private String year;

    /**
     * The total mileage of the car.
     */
    private String mileage;

    /**
     * The license plate number of the car.
     */
    private String licensePlate;

    /**
     * The timestamp indicating when the car record was created.
     */
    private LocalDateTime createdAt;

    /**
     * The timestamp indicating the last update to the car record.
     */
    private LocalDateTime updatedAt;

    /**
     * The brand name of the car.
     */
    private String brand;

    /**
     * The model name of the car.
     */
    private String carmodel;

    /**
     * The city where the car is located.
     */
    private String city;

    /**
     * The phone number of the user associated with the car.
     */
    private String userPhone;

    /**
     * The email address of the user associated with the car.
     */
    private String userEmail;

    /**
     * The availability status of the car (true if available, false otherwise).
     */
    private Boolean available;

    /**
     * Path to the uploaded image file for the car.
     */
    private String imagePath;
}
