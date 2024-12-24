package gr.aueb.cf.carrentalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarReadOnlyDTO {

    private String id;
    private String year;
    private String mileage;
    private String licensePlate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String brand;
    private String carmodel;
    private String city;
    private String model;
    private String userPhone;
    private String userEmail;
    private Boolean available;
}
