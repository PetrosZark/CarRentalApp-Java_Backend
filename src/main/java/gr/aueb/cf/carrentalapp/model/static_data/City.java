package gr.aueb.cf.carrentalapp.model.static_data;

import gr.aueb.cf.carrentalapp.model.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a city in the system.
 * A city can have multiple cars associated with it.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "cities")
public class City {

    /**
     * The unique identifier for each city.
     * This value is auto-generated.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The name of the city.
     * This value cannot be null.
     */
    @Column(nullable = false)
    private String city;

    /**
     * A set of cars associated with this city.
     * The relationship is bidirectional, mapped by the "city" field in the Car entity.
     */
    @OneToMany(mappedBy = "city")
    private Set<Car> cars = new HashSet<>();

    /**
     * Returns the city name as the string representation of the City object.
     *
     * @return City name.
     */
    @Override
    public String toString() {
        return city;
    }

    /**
     * Constructor to initialize a City with a specified name.
     *
     * @param name The name of the city.
     */
    public City(String name) {
        this.city = name;
    }
}
