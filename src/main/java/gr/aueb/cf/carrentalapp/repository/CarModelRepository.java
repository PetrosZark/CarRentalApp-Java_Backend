package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CarModelRepository extends JpaRepository<CarModel, Long>, JpaSpecificationExecutor<CarModel> {

    Optional<CarModel> findByCarmodel(String city);

}
