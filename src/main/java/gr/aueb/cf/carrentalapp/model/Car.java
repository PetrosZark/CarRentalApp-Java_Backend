package gr.aueb.cf.carrentalapp.model;

import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import jakarta.persistence.*;
import lombok.*;

/**
 * Represents a car entity in the car rental system.
 * This entity stores essential information about a car, including its brand, model, city, and availability.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cars") // Maps the entity to the 'cars' table in the database
public class Car extends AbstractEntity {

    /**
     * Unique identifier for the car (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Year the car was manufactured.
     */
    private String year;

    /**
     * Total mileage of the car.
     */
    private String mileage;

    /**
     * License plate of the car.
     * Must be unique and cannot be null.
     */
    @Column(nullable = false, unique = true)
    private String licensePlate;

    /**
     * Availability status of the car.
     * This field indicates whether the car is available for rental.
     */
    @Column(name = "available", nullable = false)
    private Boolean available;

    /**
     * Initializes default values before persisting the entity.
     * This method automatically sets the car as available by default.
     */
    @PrePersist
    public void initializeDefaults() {
        if (available == null) available = true;
    }

    /**
     * Relationship mapping to the car's brand.
     * A car is associated with exactly one brand.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    /**
     * Relationship mapping to the car model.
     * A car is associated with exactly one model.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carmodel_id", nullable = false)
    private CarModel carmodel;

    /**
     * Relationship mapping to the user who owns the car.
     * Each car must be associated with a user.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Relationship mapping to the city where the car is located.
     * A car may be associated with a city.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;
}
