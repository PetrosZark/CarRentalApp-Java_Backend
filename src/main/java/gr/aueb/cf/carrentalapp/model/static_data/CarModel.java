package gr.aueb.cf.carrentalapp.model.static_data;

import gr.aueb.cf.carrentalapp.model.Car;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a car model in the system.
 * Each car model is associated with a specific brand and can have multiple cars.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "car_models")
public class CarModel {

    /**
     * The unique identifier for each car model.
     * This value is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the car model.
     * This value cannot be null.
     */
    @Column(name = "carmodel", nullable = false)
    private String carmodel;

    /**
     * The brand associated with this car model.
     * This relationship is managed by a foreign key in the "car_models" table.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    /**
     * A set of cars associated with this car model.
     * The relationship is bidirectional, mapped by the "carmodel" field in the Car entity.
     */
    @OneToMany(mappedBy = "carmodel", cascade = CascadeType.ALL)
    private Set<Car> car = new HashSet<>();

    /**
     * Returns the model name as the string representation of the CarModel object.
     *
     * @return Car model name.
     */
    @Override
    public String toString() {
        return carmodel;
    }

    /**
     * Constructor to initialize a CarModel with a specified name.
     *
     * @param name The name of the car model.
     */
    public CarModel(String name) {
        this.carmodel = name;
    }
}
