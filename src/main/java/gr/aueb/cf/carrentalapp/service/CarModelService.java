package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectInvalidArgumentException;
import gr.aueb.cf.carrentalapp.dto.CarModelInsertDTO;
import gr.aueb.cf.carrentalapp.dto.CarModelReadOnlyDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.repository.BrandRepository;
import gr.aueb.cf.carrentalapp.repository.CarModelRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class responsible for managing car models.
 * Provides methods for retrieving, adding, and validating car models.
 */
@Service
@RequiredArgsConstructor
public class CarModelService {

    private final Mapper mapper;
    private final CarModelRepository carModelRepository;
    private final BrandRepository brandRepository;

    /**
     * Retrieves all car models from the database.
     *
     * @return List of all car models.
     */
    public List<CarModel> getAllCarModels() {
        return carModelRepository.findAll();
    }

    /**
     * Finds a car model by its name.
     *
     * @param name the name of the car model to search for.
     * @return the CarModel entity if found.
     * @throws IllegalArgumentException if the car model is not found.
     */
    public CarModel findByName(String name) {
        return carModelRepository.findByCarmodel(name)
                .orElseThrow(() -> new IllegalArgumentException("Car model not found: " + name));
    }

    /**
     * Saves a new car model to the database.
     * This method checks if the car model already exists. If it does, an exception is thrown.
     * It also validates the brand by its ID before saving the car model.
     *
     * @param dto the DTO containing the car model name and associated brand ID.
     * @return a read-only DTO representing the saved car model.
     * @throws AppObjectAlreadyExistsException if a car model with the same name already exists.
     * @throws AppObjectInvalidArgumentException if the brand ID is invalid.
     */
    @Transactional
    public CarModelReadOnlyDTO saveCarModel(CarModelInsertDTO dto)
            throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {

        // Check if the car model already exists by name
        if (carModelRepository.findByCarmodel(dto.getCarmodel()).isPresent()) {
                throw new AppObjectAlreadyExistsException
                        ("Carmodel", "Car model with name " + dto.getCarmodel() + " already exists");
            }

        // Validate brand ID
        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("Brand", "Invalid brand id"));

        // Map DTO to CarModel entity and save to the database
        CarModel carModel = mapper.mapToCarModelEntity(dto, brand);
        CarModel savedCarModel = carModelRepository.save(carModel);

        // Return the saved car model as a read-only DTO
        return mapper.mapToCarModelReadOnlyDTO(savedCarModel);
    }
}
