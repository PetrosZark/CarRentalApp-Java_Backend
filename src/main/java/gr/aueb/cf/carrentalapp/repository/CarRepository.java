package gr.aueb.cf.carrentalapp.repository;

import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

/**
 * Repository interface for managing Car entities.
 * <p>
 * Extends JpaRepository to provide basic CRUD operations.
 * Implements JpaSpecificationExecutor to enable dynamic queries using Specifications.
 * </p>
 */
public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {

    /**
     * Finds a car by its license plate.
     * <p>
     * This method returns an Optional containing the Car entity if a match is found.
     * </p>
     *
     * @param licensePlate the license plate of the car to search for
     * @return an Optional containing the Car if found, otherwise empty
     */
    Optional<Car> findByLicensePlate(String licensePlate);

    /**
     * Retrieves a paginated list of cars owned by a specific user.
     * <p>
     * Supports pagination to handle large datasets efficiently.
     * </p>
     *
     * @param user the owner of the cars
     * @param pageable the pagination information
     * @return a Page containing cars owned by the specified user
     */
    Page<Car> findByUser(User user, Pageable pageable);

    /**
     * Finds a car by its ID and associated user.
     * <p>
     * This method is useful for verifying car ownership during updates or deletions.
     * </p>
     *
     * @param id the ID of the car
     * @param user the owner of the car
     * @return an Optional containing the Car if found, otherwise empty
     */
    Optional<Car> findByIdAndUser(Long id, User user);
}
