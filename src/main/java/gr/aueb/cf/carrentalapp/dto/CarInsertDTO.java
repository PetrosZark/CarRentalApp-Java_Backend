package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarInsertDTO {

    @Pattern(regexp = "^[ABEHIKMNOPTXYZ]{3}\\d{4}$", message = "Invalid license plate.")
    private String licensePlate;

    @Pattern(regexp = "^\\d{4}$")
    private String year;

    @Pattern(regexp = "^\\d{1,7}$")
    private String mileage;

    @NotNull(message = "City cannot be empty.")
    private Long cityId;

    @NotNull(message = "Brand id cannot be null.")
    private Long brandId;

    @NotNull(message = "Car model cannot be null.")
    private Long carmodelId;

    private Boolean available;
}
