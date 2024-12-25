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

/**
 * Mapper class responsible for converting between DTOs and entity objects.
 * Facilitates data transformation to ensure consistent communication between
 * the application layers (DTOs and database entities).
 */
@Component
@RequiredArgsConstructor
public class Mapper {

    private final PasswordEncoder passwordEncoder;

    /**
     * Maps a UserInsertDTO to a User entity.
     * Passwords are encoded before being persisted.
     *
     * @param userInsertDTO The DTO containing user data.
     * @return A User entity populated with DTO values.
     */
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

    /**
     * Maps a User entity to a UserReadOnlyDTO for safe read-only client responses.
     *
     * @param user The User entity to map.
     * @return A UserReadOnlyDTO containing user information.
     */
    public UserReadOnlyDTO mapToUserReadOnlyDTO(User user) {
        var dto = new UserReadOnlyDTO();
        dto.setId(user.getId());
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
        dto.setGender(user.getGender().toString());
        return dto;
    }

    /**
     * Maps a CarInsertDTO to a Car entity.
     *
     * @param dto The DTO containing car data.
     * @param city The city entity associated with the car.
     * @param brand The brand entity associated with the car.
     * @param carModel The car model entity associated with the car.
     * @param user The user who owns the car.
     * @return A Car entity populated with DTO values.
     */
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

    /**
     * Maps a Car entity to a CarReadOnlyDTO for read-only client responses.
     *
     * @param car The Car entity to map.
     * @return A CarReadOnlyDTO containing car information.
     */
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

    /**
     * Updates an existing Car entity with data from CarUpdateDTO.
     *
     * @param dto The DTO containing updated car data.
     * @param car The existing Car entity to update.
     * @param city The city entity associated with the car.
     * @return The updated Car entity.
     */
    public Car updateCarEntity(CarUpdateDTO dto, Car car, City city) {
        car.setMileage(dto.getMileage());
        car.setAvailable(dto.getAvailable());
        car.setCity(city);
        return car;
    }

    /**
     * Maps a BrandInsertDTO to a Brand entity.
     *
     * @param dto The DTO containing brand data.
     * @return A Brand entity populated with DTO values.
     */
    public Brand mapToBrandEntity(BrandInsertDTO dto) {
        Brand brand = new Brand();
        brand.setBrand(dto.getBrand());
        return brand;
    }

    /**
     * Maps a Brand entity to a BrandReadOnlyDTO.
     *
     * @param brand The Brand entity to map.
     * @return A BrandReadOnlyDTO containing brand information.
     */
    public BrandReadOnlyDTO mapToBrandReadOnlyDTO(Brand brand) {
        var dto = new BrandReadOnlyDTO();
        dto.setBrand(brand.getBrand());
        dto.setId(brand.getId());
        return dto;
    }

    /**
     * Maps a CarModelInsertDTO to a CarModel entity.
     *
     * @param dto The DTO containing car model data.
     * @param brand The brand entity associated with the car model.
     * @return A CarModel entity populated with DTO values.
     */
    public CarModel mapToCarModelEntity(CarModelInsertDTO dto, Brand brand) {
        CarModel carModel = new CarModel();
        carModel.setCarmodel(dto.getCarmodel());
        carModel.setBrand(brand);
        return carModel;
    }

    /**
     * Maps a CarModel entity to a CarModelReadOnlyDTO.
     *
     * @param carModel The CarModel entity to map.
     * @return A CarModelReadOnlyDTO containing car model information.
     */
    public CarModelReadOnlyDTO mapToCarModelReadOnlyDTO(CarModel carModel) {
        var dto = new CarModelReadOnlyDTO();
        dto.setId(carModel.getId());
        dto.setCarmodel(carModel.getCarmodel());
        dto.setBrand(carModel.getBrand() != null ? carModel.getBrand().toString() : "N/A");
        return dto;
    }

    /**
     * Maps a CityInsertDTO to a City entity.
     *
     * @param dto The DTO containing city data.
     * @return A City entity populated with DTO values.
     */
    public City mapToCityEntity(CityInsertDTO dto) {
        City city= new City();
        city.setCity(dto.getCity());
        return city;
    }

    /**
     * Maps a City entity to a CityReadOnlyDTO.
     *
     * @param city The City entity to map.
     * @return A CityReadOnlyDTO containing city information.
     */
    public CityReadOnlyDTO mapToCityReadOnlyDTO(City city) {
        var dto = new CityReadOnlyDTO();
        dto.setCity(city.getCity());
        dto.setId(city.getId());
        return dto;
    }
}
