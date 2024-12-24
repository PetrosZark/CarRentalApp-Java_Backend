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


@Service
@RequiredArgsConstructor
public class CarModelService {

    private final Mapper mapper;
    private final CarModelRepository carModelRepository;
    private final BrandRepository brandRepository;

    public List<CarModel> getAllCarModels() {
        return carModelRepository.findAll();
    }

    public CarModel findByName(String name) {
        return carModelRepository.findByCarmodel(name)
                .orElseThrow(() -> new IllegalArgumentException("Car model not found: " + name));
    }

    @Transactional
    public CarModelReadOnlyDTO saveCarModel(CarModelInsertDTO dto)
            throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {


            if (carModelRepository.findByCarmodel(dto.getCarmodel()).isPresent()) {
                throw new AppObjectAlreadyExistsException
                        ("Carmodel", "Car model with name " + dto.getCarmodel() + " already exists");
            }

            Brand brand = brandRepository.findById(dto.getBrandId())
                    .orElseThrow(() -> new AppObjectInvalidArgumentException("Brand", "Invalid brand id"));

            CarModel carModel = mapper.mapToCarModelEntity(dto, brand);
            CarModel savedCarModel = carModelRepository.save(carModel);
            return mapper.mapToCarModelReadOnlyDTO(savedCarModel);
    }
}
