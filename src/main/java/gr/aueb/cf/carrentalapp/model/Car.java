package gr.aueb.cf.carrentalapp.model;

import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "cars")
public class Car extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uuid;

    private String year;
    private String mileage;
    @Column(nullable = false, unique = true)
    private String licensePlate;

    @Column(name = "available", nullable = false)
    private Boolean available;

    @PrePersist
    public void initializeDefaults() {
        if (uuid == null) uuid = UUID.randomUUID().toString();
        if (available == null) available = true;
    }

    public Car(LocalDateTime createdAt, LocalDateTime updatedAt, Long id, String year,
               String mileage, String licensePlate,
               Brand brand, CarModel carmodel, User user) {
        this.id = id;
        this.year = year;
        this.mileage = mileage;
        this.licensePlate = licensePlate;
        this.brand = brand;
        this.carmodel = carmodel;
        this.user = user;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carmodel_id", nullable = false)
    private CarModel carmodel;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id")
    private City city;
}
