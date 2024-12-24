package gr.aueb.cf.carrentalapp.core.filters;

import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import lombok.*;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CarFilters extends GenericFilters {

    @Nullable
    private String uuid;

    @Nullable
    private String licensePlate;

    @Nullable
    private Boolean available;

    @Nullable
    private Brand brand;

    @Nullable
    private CarModel carmodel;

    @Nullable
    private User user;

    @Nullable
    private City city;

    public static class CarFiltersBuilder {
        private final CarFilters instance = new CarFilters();

        public CarFiltersBuilder page(int page) {
            instance.setPage(page);
            return this;
        }

        public CarFiltersBuilder pageSize(int pageSize) {
            instance.setPageSize(pageSize);
            return this;
        }

        public CarFiltersBuilder sortBy(String sortBy) {
            instance.setSortBy(sortBy);
            return this;
        }

        public CarFiltersBuilder sortDirection(Sort.Direction sortDirection) {
            instance.setSortDirection(sortDirection);
            return this;
        }

        public CarFiltersBuilder uuid(String uuid) {
            instance.setUuid(uuid);
            return this;
        }

        public CarFiltersBuilder licensePlate(String licensePlate) {
            instance.setLicensePlate(licensePlate);
            return this;
        }

        public CarFiltersBuilder available(Boolean available) {
            instance.setAvailable(available);
            return this;
        }

        public CarFiltersBuilder brand(Brand brand) {
            instance.setBrand(brand);
            return this;
        }

        public CarFiltersBuilder user(User user) {
            instance.setUser(user);
            return this;
        }

        public CarFiltersBuilder carmodel(CarModel carmodel) {
            instance.setCarmodel(carmodel);
            return this;
        }

        public CarFiltersBuilder city(City city) {
            instance.setCity(city);
            return this;
        }

        public CarFilters build() {
            return instance;
        }
    }

    public static CarFiltersBuilder builder() {
        return new CarFiltersBuilder();
    }
}
