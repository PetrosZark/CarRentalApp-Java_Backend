package gr.aueb.cf.carrentalapp.model.static_data;

import gr.aueb.cf.carrentalapp.model.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "cities")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String city;

    @OneToMany(mappedBy = "city")
    private Set<Car> cars = new HashSet<>();

    @Override
    public String toString() {
        return city;
    }

    public City(String name) {
        this.city = name;
    }
}
