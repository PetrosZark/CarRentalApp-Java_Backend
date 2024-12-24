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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BrandService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrandService.class);
    private final BrandRepository brandRepository;
    private final Mapper mapper;

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Brand findByName(String name) {
        return brandRepository.findByBrand(name)
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + name));
    }

    @Transactional
    public BrandReadOnlyDTO saveBrand(BrandInsertDTO dto) throws AppObjectAlreadyExistsException {

        if (brandRepository.findByBrand(dto.getBrand()).isPresent()) {
            throw new AppObjectAlreadyExistsException
                    ("Brand", "Brand with name " + dto.getBrand() + " already exists");
        }

        Brand brand = mapper.mapToBrandEntity(dto);
        Brand savedBrand = brandRepository.save(brand);
        return mapper.mapToBrandReadOnlyDTO(savedBrand);
    }


}
