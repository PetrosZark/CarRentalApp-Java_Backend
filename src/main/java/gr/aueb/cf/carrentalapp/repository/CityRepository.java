package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.static_data.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Long>, JpaSpecificationExecutor {

        Optional<City> findByCity(String city);
}

