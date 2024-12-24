package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

/**
 * Repository interface for managing CarModel entities.
 * Extends JpaRepository to provide standard CRUD operations.
 * Implements JpaSpecificationExecutor to allow dynamic queries using Specifications.
 */
public interface CarModelRepository extends JpaRepository<CarModel, Long>, JpaSpecificationExecutor<CarModel> {

    /**
     * Retrieves a CarModel entity by its model name.
     * <p>
     * This method returns an Optional containing the CarModel if found.
     * </p>
     *
     * @param carmodel the name of the car model to search for
     * @return an Optional containing the CarModel entity if found, otherwise empty
     */
    Optional<CarModel> findByCarmodel(String carmodel);

}
