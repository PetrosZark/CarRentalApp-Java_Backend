package gr.aueb.cf.carrentalapp.mapper;

import gr.aueb.cf.carrentalapp.dto.*;
import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.model.static_data.Brand;
import gr.aueb.cf.carrentalapp.model.static_data.CarModel;
import gr.aueb.cf.carrentalapp.model.static_data.City;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Mapper {

    private final PasswordEncoder passwordEncoder;


    public User mapToUserEntity(UserInsertDTO userInsertDTO) {

        User user = new User();
        user.setUsername(userInsertDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userInsertDTO.getPassword())); // Hash here
        user.setFirstname(userInsertDTO.getFirstname());
        user.setLastname(userInsertDTO.getLastname());
        user.setEmail(userInsertDTO.getEmail());
        user.setVat(userInsertDTO.getVat());
        user.setPhone(userInsertDTO.getPhone());
        user.setDateOfBirth(userInsertDTO.getDateOfBirth());
        user.setGender(userInsertDTO.getGender());
        user.setRole(userInsertDTO.getRole());
        user.setIsActive(userInsertDTO.getIsActive());
        return user;
    }

    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        var dto = new UserReadOnlyDTO();
        dto.setId(user.getId());
        dto.setUuid(user.getUuid());
        dto.setUsername(user.getUsername());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setEmail(user.getEmail());
        dto.setDateOfBirth(user.getDateOfBirth());
        dto.setVat(user.getVat());
        dto.setPhone(user.getPhone());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());
        dto.setIsActive(user.getIsActive());
        dto.setRole(user.getRole().toString());
        return dto;
    }

    public Car mapToCarEntity(CarInsertDTO dto, City city, Brand brand, CarModel carModel, User user) {

        Car car = new Car();
        car.setYear(dto.getYear());
        car.setMileage(dto.getMileage());
        car.setLicensePlate(dto.getLicensePlate());
        car.setCity(city);
        car.setBrand(brand);
        car.setCarmodel(carModel);
        car.setAvailable(dto.getAvailable());
        car.setUser(user);
        return car;
    }

    public CarReadOnlyDTO mapToCarReadOnlyDTO(Car car) {
        var dto = new CarReadOnlyDTO();
        dto.setId(car.getId().toString());
        dto.setYear(car.getYear());
        dto.setMileage(car.getMileage());
        dto.setLicensePlate(car.getLicensePlate());
        dto.setCity(car.getCity() != null ? car.getCity().toString() : "N/A");
        dto.setBrand(car.getBrand() != null ? car.getBrand().toString() : "N/A");
        dto.setCarmodel(car.getCarmodel() != null ? car.getCarmodel().toString() : "N/A");
        dto.setCreatedAt(car.getCreatedAt());
        dto.setUpdatedAt(car.getUpdatedAt());
        dto.setUserPhone(car.getUser().getPhone());
        dto.setUserEmail(car.getUser().getEmail());
        dto.setAvailable(car.getAvailable());
        return dto;
    }

    public Car updateCarEntity(CarUpdateDTO dto, Car car, City city) {
        car.setMileage(dto.getMileage());
        car.setAvailable(dto.getAvailable());
        car.setCity(city);
        return car;
    }

    public Brand mapToBrandEntity(BrandInsertDTO dto) {
        Brand brand = new Brand();
        brand.setBrand(dto.getBrand());
        return brand;
    }

    public BrandReadOnlyDTO mapToBrandReadOnlyDTO(Brand brand) {
        var dto = new BrandReadOnlyDTO();
        dto.setBrand(brand.getBrand());
        dto.setId(brand.getId());
        return dto;
    }

    public CarModel mapToCarModelEntity(CarModelInsertDTO dto, Brand brand) {
        CarModel carModel = new CarModel();
        carModel.setCarmodel(dto.getCarmodel());
        carModel.setBrand(brand);
        return carModel;
    }

    public CarModelReadOnlyDTO mapToCarModelReadOnlyDTO(CarModel carModel) {
        var dto = new CarModelReadOnlyDTO();
        dto.setId(carModel.getId());
        dto.setCarmodel(carModel.getCarmodel());
        dto.setBrand(carModel.getBrand() != null ? carModel.getBrand().toString() : "N/A");
        return dto;
    }

    public City mapToCityEntity(CityInsertDTO dto) {
        City city= new City();
        city.setCity(dto.getCity());
        return city;
    }

    public CityReadOnlyDTO mapToCityReadOnlyDTO(City city) {
        var dto = new CityReadOnlyDTO();
        dto.setCity(city.getCity());
        dto.setId(city.getId());
        return dto;
    }
}
