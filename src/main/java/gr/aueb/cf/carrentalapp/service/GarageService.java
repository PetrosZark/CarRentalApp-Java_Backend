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


@Service
@RequiredArgsConstructor
public class GarageService {

    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarModelRepository carModelRepository;
    private final Mapper mapper;
    private final CityRepository cityRepository;

    @Transactional
    public Page<CarReadOnlyDTO> getUsersPaginatedCars(String username, int page, int size) throws AppObjectNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User not found with username: " + username));


        Page<Car> carPage = carRepository.findByUser(user, PageRequest.of(page, size));

        return carPage.map(mapper::mapToCarReadOnlyDTO);
    }

    @Transactional(rollbackOn = Exception.class)
    public CarReadOnlyDTO saveCar(User loggedInUser, CarInsertDTO dto)
            throws AppObjectAlreadyExistsException, AppObjectInvalidArgumentException {

        if (carRepository.findByLicensePlate(dto.getLicensePlate()).isPresent()) {
            throw new AppObjectAlreadyExistsException
                    ("Car", "Car with license plate " + dto.getLicensePlate() + " already exists");
        }

        Brand brand = brandRepository.findById(dto.getBrandId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("Brand", "Invalid brand id"));

        CarModel carModel = carModelRepository.findById(dto.getCarmodelId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("Model", "Invalid model id"));

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("City", "Invalid city id"));


        Car car = mapper.mapToCarEntity(dto, city, brand, carModel, loggedInUser);


        Car savedCar = carRepository.save(car);
        return mapper.mapToCarReadOnlyDTO(savedCar);
    }

    @Transactional
    public CarReadOnlyDTO updateCar(User loggedInUser, Long carId, CarUpdateDTO dto) throws AppObjectNotFoundException, AppObjectInvalidArgumentException {

        City city = cityRepository.findById(dto.getCityId())
                .orElseThrow(() -> new AppObjectInvalidArgumentException("City", "Invalid city id"));

        Car car = carRepository.findByIdAndUser(carId, loggedInUser)
                .orElseThrow(() -> new AppObjectNotFoundException("Car","Car not found"));


        Car updatedCar = mapper.updateCarEntity(dto, car, city);
        carRepository.save(updatedCar);
        return mapper.mapToCarReadOnlyDTO(updatedCar);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteCar(User user, Long carId) throws AppObjectNotFoundException {

        Car car = carRepository.findByIdAndUser(carId, user)
                .orElseThrow(() -> new AppObjectNotFoundException("Car","Car not found"));
        carRepository.delete(car);
    }
}







