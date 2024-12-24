package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.dto.CityInsertDTO;
import gr.aueb.cf.carrentalapp.dto.CityReadOnlyDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import gr.aueb.cf.carrentalapp.repository.CityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing city-related operations.
 * Provides methods to retrieve all cities, find a city by name, and save a new city.
 */
@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final Mapper mapper;

    /**
     * Retrieves a list of all cities from the database.
     *
     * @return List of City entities.
     */
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    /**
     * Finds a city by its name.
     * Throws an exception if the city is not found.
     *
     * @param name The name of the city to search for.
     * @return City entity if found.
     * @throws IllegalArgumentException if the city is not found.
     */
    public City findByName(String name) {
        return cityRepository.findByCity(name)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + name));
    }

    /**
     * Saves a new city to the database.
     * Performs a check to ensure the city does not already exist.
     *
     * @param dto Data Transfer Object (DTO) containing the city details.
     * @return A read-only DTO representing the saved city.
     * @throws AppObjectAlreadyExistsException if a city with the same name already exists.
     */
    @Transactional
    public CityReadOnlyDTO saveCity(CityInsertDTO dto) throws AppObjectAlreadyExistsException {

        // Check if a city with the same name already exists
        if (cityRepository.findByCity(dto.getCity()).isPresent()) {
            throw new AppObjectAlreadyExistsException
                    ("City", "City with name " + dto.getCity() + " already exists");
        }

        // Map DTO to City entity and save it to the database
        City city = mapper.mapToCityEntity(dto);
        City savedCity = cityRepository.save(city);

        // Convert the saved entity to a read-only DTO and return
        return mapper.mapToCityReadOnlyDTO(savedCity);
    }
}
