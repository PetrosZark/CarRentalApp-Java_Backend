package gr.aueb.cf.carrentalapp.model.static_data;

import gr.aueb.cf.carrentalapp.model.Car;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "brands")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "brand", nullable = false, unique = true)
    private String brand;

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private Set<Car> car = new HashSet<>();

    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private Set<CarModel> carModel = new HashSet<>();

    @Override
    public String toString() {
        return brand;
    }

    public Brand(String name) {
        this.brand = name;
    }
}
