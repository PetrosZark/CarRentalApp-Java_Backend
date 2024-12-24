package gr.aueb.cf.carrentalapp.rest;

import gr.aueb.cf.carrentalapp.core.exceptions.*;
import gr.aueb.cf.carrentalapp.dto.CarInsertDTO;
import gr.aueb.cf.carrentalapp.dto.CarReadOnlyDTO;
import gr.aueb.cf.carrentalapp.dto.CarUpdateDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.repository.CarRepository;
import gr.aueb.cf.carrentalapp.service.GarageService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/home/garage")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class GarageRestController {

    private final GarageService garageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageRestController.class);
    private final CarRepository carRepository;
    private final Mapper mapper;



    @GetMapping()
    public ResponseEntity<Page<CarReadOnlyDTO>> getUsersPaginatedCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws AppObjectNotFoundException{

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.info("Authenticated username: {}", username);

        Page<CarReadOnlyDTO> carsPage = garageService.getUsersPaginatedCars(username, page, size);
        return new ResponseEntity<>(carsPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarReadOnlyDTO> saveCar(
            @AuthenticationPrincipal User loggedInUser,
            @RequestBody @Valid CarInsertDTO carInsertDTO,
            BindingResult bindingResult)
            throws AppObjectInvalidArgumentException, ValidationException, AppObjectAlreadyExistsException {

        // Check if there are validation errors
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        CarReadOnlyDTO carReadOnlyDTO = garageService.saveCar(loggedInUser, carInsertDTO);
        return new ResponseEntity<>(carReadOnlyDTO, HttpStatus.CREATED);

    }

    @PutMapping("/update/{carId}")
    public ResponseEntity<CarReadOnlyDTO> updateCar (
            @AuthenticationPrincipal User loggedInUser,
            @PathVariable Long carId,
            @Valid @RequestBody CarUpdateDTO carUpdateDTO,
            BindingResult bindingResult)
            throws AppObjectNotFoundException, AppObjectInvalidArgumentException {

        if (bindingResult.hasErrors()) {
            throw new AppObjectInvalidArgumentException("Car", "Car not updated");
        }

        CarReadOnlyDTO updatedCar = garageService.updateCar(loggedInUser, carId, carUpdateDTO) ;
        return new ResponseEntity<>(updatedCar, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId, @AuthenticationPrincipal User user)
        throws AppObjectNotFoundException
    {
            garageService.deleteCar(user, carId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{carId}")
    public ResponseEntity<CarReadOnlyDTO> getCarById(@PathVariable Long carId) throws AppObjectNotFoundException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new AppObjectNotFoundException("Car", "Car not found"));
        return ResponseEntity.ok(mapper.mapToCarReadOnlyDTO(car));
    }
}





