package gr.aueb.cf.carrentalapp.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarModelInsertDTO {

    @NotNull(message = "Car model name cannot be empty.")
    private String carmodel;

    @NotNull(message = "Brand id cannot be empty.")
    private Long brandId;
}
