package gr.aueb.cf.carrentalapp.model.static_data;

import gr.aueb.cf.carrentalapp.model.Car;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "car_models")
public class CarModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "carmodel", nullable = false)
    private String carmodel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @OneToMany(mappedBy = "carmodel", cascade = CascadeType.ALL)
    private Set<Car> car = new HashSet<>();

    @Override
    public String toString() {
        return carmodel;
    }

    public CarModel(String name) {
        this.carmodel = name;
    }
}
