package gr.aueb.cf.carrentalapp.core.filters;

import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import lombok.*;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;

/**
 * Filter class for applying various search and filtering criteria to car entities.
 * This class allows the construction of dynamic filter queries through its builder pattern.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CarFilters extends GenericFilters {

    /**
     * Car's license plate number.
     */
    @Nullable
    private String licensePlate;

    /**
     * Availability status of the car (true if available, false otherwise).
     */
    @Nullable
    private Boolean available;

    /**
     * The brand of the car.
     */
    @Nullable
    private Brand brand;

    /**
     * The model of the car.
     */
    @Nullable
    private CarModel carmodel;

    /**
     * The user associated with the car (owner).
     */
    @Nullable
    private User user;

    /**
     * The city where the car is located.
     */
    @Nullable
    private City city;

    /**
     * Builder class for creating instances of {@link CarFilters} with method chaining.
     */
    public static class CarFiltersBuilder {
        private final CarFilters instance = new CarFilters();

        /**
         * Sets the page number for pagination.
         *
         * @param page The page number to retrieve.
         * @return The current builder instance.
         */
        public CarFiltersBuilder page(int page) {
            instance.setPage(page);
            return this;
        }

        /**
         * Sets the size of each page for pagination.
         *
         * @param pageSize The number of results per page.
         * @return The current builder instance.
         */
        public CarFiltersBuilder pageSize(int pageSize) {
            instance.setPageSize(pageSize);
            return this;
        }

        /**
         * Sets the sorting field for the query results.
         *
         * @param sortBy The field by which to sort results.
         * @return The current builder instance.
         */
        public CarFiltersBuilder sortBy(String sortBy) {
            instance.setSortBy(sortBy);
            return this;
        }

        /**
         * Sets the sorting direction (ascending or descending).
         *
         * @param sortDirection The direction of sorting.
         * @return The current builder instance.
         */
        public CarFiltersBuilder sortDirection(Sort.Direction sortDirection) {
            instance.setSortDirection(sortDirection);
            return this;
        }


        /**
         * Sets the car's license plate filter.
         *
         * @param licensePlate The car's license plate number.
         * @return The current builder instance.
         */
        public CarFiltersBuilder licensePlate(String licensePlate) {
            instance.setLicensePlate(licensePlate);
            return this;
        }

        /**
         * Sets the availability filter for the car.
         *
         * @param available Car availability status.
         * @return The current builder instance.
         */
        public CarFiltersBuilder available(Boolean available) {
            instance.setAvailable(available);
            return this;
        }

        /**
         * Sets the car's brand filter.
         *
         * @param brand The car's brand.
         * @return The current builder instance.
         */
        public CarFiltersBuilder brand(Brand brand) {
            instance.setBrand(brand);
            return this;
        }

        /**
         * Sets the user filter for the car.
         *
         * @param user The user associated with the car.
         * @return The current builder instance.
         */
        public CarFiltersBuilder user(User user) {
            instance.setUser(user);
            return this;
        }

        /**
         * Sets the car model filter.
         *
         * @param carmodel The car's model.
         * @return The current builder instance.
         */public CarFiltersBuilder carmodel(CarModel carmodel) {
            instance.setCarmodel(carmodel);
            return this;
        }

        /**
         * Sets the city filter for the car.
         *
         * @param city The city where the car is located.
         * @return The current builder instance.
         */
        public CarFiltersBuilder city(City city) {
            instance.setCity(city);
            return this;
        }

        /**
         * Builds and returns an instance of {@link CarFilters}.
         *
         * @return A fully constructed CarFilters object.
         */
        public CarFilters build() {
            return instance;
        }
    }

    /**
     * Provides access to the builder for constructing CarFilters instances.
     *
     * @return A new instance of {@link CarFiltersBuilder}.
     */
    public static CarFiltersBuilder builder() {
        return new CarFiltersBuilder();
    }
}
