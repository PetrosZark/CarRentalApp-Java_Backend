package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long>, JpaSpecificationExecutor<Brand> {

    Optional<Brand> findByBrand(String brand);

}
