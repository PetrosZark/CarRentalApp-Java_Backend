package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.dto.BrandInsertDTO;
import gr.aueb.cf.carrentalapp.dto.BrandReadOnlyDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.repository.BrandRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


/**
 * Service class responsible for handling brand-related operations.
 * Provides methods for retrieving, adding, and managing car brands.
 */
@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final Mapper mapper;

    /**
     * Retrieves all brands from the database.
     *
     * @return List of all brands.
     */
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * Finds a brand by its name.
     *
     * @param name the name of the brand to search for.
     * @return the Brand entity if found.
     * @throws IllegalArgumentException if the brand is not found.
     */
    public Brand findByName(String name) {
        return brandRepository.findByBrand(name)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + name));
    }

    /**
     * Saves a new brand to the database.
     * Before saving, checks if a brand with the same name already exists.
     * If the brand exists, throws an AppObjectAlreadyExistsException.
     *
     * @param dto the DTO containing the brand name to be inserted.
     * @return a read-only DTO representing the saved brand.
     * @throws AppObjectAlreadyExistsException if a brand with the same name already exists.
     */
    @Transactional
    public BrandReadOnlyDTO saveBrand(BrandInsertDTO dto) throws AppObjectAlreadyExistsException {

        // Check if the brand already exists by name
        if (brandRepository.findByBrand(dto.getBrand()).isPresent()) {
            throw new AppObjectAlreadyExistsException
                    ("Brand", "Brand with name " + dto.getBrand() + " already exists");
        }

        // Map DTO to Brand entity and save it to the database
        Brand brand = mapper.mapToBrandEntity(dto);
        Brand savedBrand = brandRepository.save(brand);

        // Return the saved brand as a read-only DTO
        return mapper.mapToBrandReadOnlyDTO(savedBrand);
    }


}
