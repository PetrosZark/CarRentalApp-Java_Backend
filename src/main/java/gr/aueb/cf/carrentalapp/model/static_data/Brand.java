package gr.aueb.cf.carrentalapp.model.static_data;

import gr.aueb.cf.carrentalapp.model.Car;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a car brand in the system.
 * This entity is linked to cars and car models, establishing relationships
 * between the brand and associated cars or models.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "brands")
public class Brand {

    /**
     * The unique identifier for each brand.
     * This value is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the brand.
     * This value must be unique and cannot be null.
     */
    @Column(name = "brand", nullable = false, unique = true)
    private String brand;

    /**
     * A set of cars associated with this brand.
     * The relationship is bidirectional, mapped by the "brand" field in the Car entity.
     */
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private Set<Car> car = new HashSet<>();

    /**
     * A set of car models associated with this brand.
     * The relationship is bidirectional, mapped by the "brand" field in the CarModel entity.
     */
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private Set<CarModel> carModel = new HashSet<>();

    /**
     * Returns the brand name as the string representation of the Brand object.
     *
     * @return Brand name.
     */
    @Override
    public String toString() {
        return brand;
    }

    /**
     * Constructor to initialize a Brand with a specified name.
     *
     * @param name The name of the brand.
     */
    public Brand(String name) {
        this.brand = name;
    }
}
