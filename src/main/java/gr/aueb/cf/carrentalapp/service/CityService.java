package gr.aueb.cf.carrentalapp.service;

import gr.aueb.cf.carrentalapp.core.exceptions.AppObjectAlreadyExistsException;
import gr.aueb.cf.carrentalapp.dto.BrandInsertDTO;
import gr.aueb.cf.carrentalapp.dto.BrandReadOnlyDTO;
import gr.aueb.cf.carrentalapp.dto.CityInsertDTO;
import gr.aueb.cf.carrentalapp.dto.CityReadOnlyDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import gr.aueb.cf.carrentalapp.repository.BrandRepository;
import gr.aueb.cf.carrentalapp.repository.CityRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityService {

    private final CityRepository cityRepository;
    private final Mapper mapper;

    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    public City findByName(String name) {
        return cityRepository.findByCity(name)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + name));
    }

    @Transactional
    public CityReadOnlyDTO saveCity(CityInsertDTO dto) throws AppObjectAlreadyExistsException {

        if (cityRepository.findByCity(dto.getCity()).isPresent()) {
            throw new AppObjectAlreadyExistsException
                    ("City", "City with name " + dto.getCity() + " already exists");
        }

        City city = mapper.mapToCityEntity(dto);
        City savedCity = cityRepository.save(city);
        return mapper.mapToCityReadOnlyDTO(savedCity);
    }
}
