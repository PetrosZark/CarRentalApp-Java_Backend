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
public class CarUpdateDTO {

    @Pattern(regexp = "^\\d{1,7}$")
    private String mileage;

    @NotNull(message = "City cannot be empty.")
    private Long cityId;

    private Boolean available;

}
