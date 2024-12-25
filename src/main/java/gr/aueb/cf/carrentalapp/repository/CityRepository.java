package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.static_data.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

/**
 * Repository interface for managing City entities.
 * <p>
 * Extends JpaRepository to provide standard CRUD operations.
 * Implements JpaSpecificationExecutor to allow dynamic and flexible queries.
 * </p>
 */
public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor<City> {

        /**
         * Finds a city by its name.
         * <p>
         * This method searches for a City entity based on the provided city name.
         * </p>
         *
         * @param city the name of the city to search for
         * @return an Optional containing the City entity if found, otherwise empty
         */
        Optional<City> findByCity(String city);
}

