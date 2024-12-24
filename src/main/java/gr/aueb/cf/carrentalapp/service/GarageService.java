package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.carrentalapp.dto.CarInsertDTO;
import gr.aueb.cf.carrentalapp.dto.CarReadOnlyDTO;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.carrentalapp.dto.CarUpdateDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import gr.aueb.cf.carrentalapp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


/**
 * Service class for managing garage-related operations.
 * Handles car addition, retrieval, updates, and deletion for authenticated users.
 */
@Service
@RequiredArgsConstructor
public class GarageService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarModelRepository carModelRepository;
    private final Mapper mapper;
    private final CityRepository cityRepository;

    /**
     * Retrieves a paginated list of cars associated with the authenticated user.
     *
     * @param username the username of the authenticated user
     * @param page the page number to retrieve
     * @param size the number of records per page
     * @return Paginated list of cars mapped to CarReadOnlyDTO
     * @throws AppObjectNotFoundException if the user is not found
     */
    @Transactional
    public Page<CarReadOnlyDTO> getUsersPaginatedCars(String username, int page, int size) throws AppObjectNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User not found with username: " + username));


        // Fetch cars for the user with pagination
        Page<Car> carPage = carRepository.findByUser(user, PageRequest.of(page, size));

        // Map to DTO for read-only response
        return carPage.map(mapper::mapToCarReadOnlyDTO);
    }

    /**
     * Saves a new car to the authenticated user's garage.
     * Validates car license plate uniqueness and the validity of related brand, model, and city.
     *
     * @param loggedInUser the authenticated user
     * @param dto the car data to be inserted
     * @return CarReadOnlyDTO of the saved car
     * @throws AppObjectAlreadyExistsException if the car already exists by license plate
     * @throws AppObjectInvalidArgumentException if brand, model, or city IDs are invalid
     */
    @Transactional(rollbackOn = Exception.class)
    public CarReadOnlyDTO saveCar(User loggedInUser, CarInsertDTO dto)
            throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {

        // Check if a car with the same license plate already exists
        if (carRepository.findByLicensePlate(dto.getLicensePlate()).isPresent()) {
            throw new AppObjectAlreadyExistsException
                    ("Car", "Car with license plate " + dto.getLicensePlate() + " already exists");
        }

        // Validate brand existence
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("Brand", "Invalid brand id"));

        // Validate car model existence
        CarModel carModel = carModelRepository.findById(dto.getCarmodelId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("Model", "Invalid model id"));

        // Validate city existence
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("City", "Invalid city id"));


        // Map DTO to Car entity
        Car car = mapper.mapToCarEntity(dto, city, brand, carModel, loggedInUser);


        // Save the car entity to the database
        Car savedCar = carRepository.save(car);

        // Return a read-only DTO representing the saved car
        return mapper.mapToCarReadOnlyDTO(savedCar);
    }

    /**
     * Updates an existing car owned by the authenticated user.
     * Validates the car existence and city ID.
     *
     * @param loggedInUser the authenticated user
     * @param carId the ID of the car to update
     * @param dto the car update data
     * @return CarReadOnlyDTO of the updated car
     * @throws AppObjectNotFoundException if the car is not found for the user
     * @throws AppObjectInvalidArgumentException if the city ID is invalid
     */
    @Transactional
    public CarReadOnlyDTO updateCar(User loggedInUser, Long carId, CarUpdateDTO dto) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {

        // Validate city existence for the update
        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("City", "Invalid city id"));

        // Find the car by ID and ensure it belongs to the authenticated user
        Car car = carRepository.findByIdAndUser(carId, loggedInUser)
                .orElseThrow(() -> new AppObjectNotFoundException("Car","Car not found"));


        // Update the car entity with the provided data
        Car updatedCar = mapper.updateCarEntity(dto, car, city);

        // Save the updated car to the database
        carRepository.save(updatedCar);

        // Return the updated car as a read-only DTO
        return mapper.mapToCarReadOnlyDTO(updatedCar);
    }

    /**
     * Deletes a car from the authenticated user's garage.
     * Ensures the car belongs to the user before deletion.
     *
     * @param user the authenticated user
     * @param carId the ID of the car to delete
     * @throws AppObjectNotFoundException if the car is not found for the user
     */
    @Transactional(rollbackOn = Exception.class)
    public void deleteCar(User user, Long carId) throws AppObjectNotFoundException {

        // Find the car by ID and ensure it belongs to the authenticated user
        Car car = carRepository.findByIdAndUser(carId, user)
                .orElseThrow(() -> new AppObjectNotFoundException("Car","Car not found"));

        // Delete the car from the database
        carRepository.delete(car);
    }
}







