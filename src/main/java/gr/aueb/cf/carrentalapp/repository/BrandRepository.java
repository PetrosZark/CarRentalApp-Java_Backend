package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

/**
 * Repository interface for managing Brand entities.
 * Extends JpaRepository to provide basic CRUD operations.
 * Implements JpaSpecificationExecutor to support dynamic queries using Specifications.
 */
public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    /**
     * Retrieves a Brand entity by its brand name.
     *
     * @param brand the name of the brand to search for
     * @return an Optional containing the Brand entity if found, otherwise empty
     */
    Optional<Brand> findByBrand(String brand);

}
