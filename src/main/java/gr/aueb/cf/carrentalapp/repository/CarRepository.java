package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    Optional<Car> findByLicensePlate(String licensePlate);

    Page<Car> findByUser(User user, Pageable pageable);

    Optional<Car> findByIdAndUser(Long id, User user);
}
