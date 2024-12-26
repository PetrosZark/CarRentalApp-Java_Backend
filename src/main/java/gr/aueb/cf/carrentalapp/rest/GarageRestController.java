package gr.aueb.cf.carrentalapp.rest;

import gr.aueb.cf.carrentalapp.core.exceptions.*;
import gr.aueb.cf.carrentalapp.dto.CarInsertDTO;
import gr.aueb.cf.carrentalapp.dto.CarReadOnlyDTO;
import gr.aueb.cf.carrentalapp.dto.CarUpdateDTO;
import gr.aueb.cf.carrentalapp.dto.ResponseMessageDTO;
import gr.aueb.cf.carrentalapp.mapper.Mapper;
import gr.aueb.cf.carrentalapp.model.Attachment;
import gr.aueb.cf.carrentalapp.model.Car;
import gr.aueb.cf.carrentalapp.model.User;
import gr.aueb.cf.carrentalapp.repository.CarRepository;
import gr.aueb.cf.carrentalapp.service.AttachmentService;
import gr.aueb.cf.carrentalapp.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import org.springframework.web.multipart.MultipartFile;



/**
 * REST controller for managing car-related operations in the user's garage.
 * Provides endpoints for retrieving, adding, updating, and deleting cars.
 */
@RestController
@RequestMapping("/api/home/garage")
@SecurityRequirement(name = "Bearer Authentication") // Require JWT token for all endpoints
@RequiredArgsConstructor
public class GarageRestController {

    private final GarageService garageService;
    private final AttachmentService attachmentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(GarageRestController.class);
    private final CarRepository carRepository;
    private final Mapper mapper;

    /**
     * Retrieves paginated cars for the authenticated user.
     *
     * @param page the page number (default 0)
     * @param size the number of records per page (default 5)
     * @return paginated list of cars owned by the authenticated user
     * @throws AppObjectNotFoundException if the user is not found
     */
    @GetMapping()
    public ResponseEntity<Page<CarReadOnlyDTO>> getUsersPaginatedCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) throws AppObjectNotFoundException{

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        LOGGER.info("Authenticated username: {}", username);

        Page<CarReadOnlyDTO> carsPage = garageService.getUsersPaginatedCars(username, page, size);
        return new ResponseEntity<>(carsPage, HttpStatus.OK);
    }

    /**
     * Saves a new car to the authenticated user's garage.
     *
     * @param loggedInUser the authenticated user
     * @param carInsertDTO the car data for insertion
     * @param bindingResult validation results for car data
     * @return the saved car details
     * @throws ValidationException if validation fails
     * @throws AppObjectAlreadyExistsException if the car already exists
     */
    @Operation(summary = "Save a new car for the authenticated user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Car successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated")
    })
    @PostMapping
    public ResponseEntity<CarReadOnlyDTO> saveCar(
            @AuthenticationPrincipal User loggedInUser,
            @RequestBody @Valid CarInsertDTO carInsertDTO,
            BindingResult bindingResult)
            throws AppObjectInvalidArgumentException, ValidationException, AppObjectAlreadyExistsException {

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        CarReadOnlyDTO carReadOnlyDTO = garageService.saveCar(loggedInUser, carInsertDTO);
        return new ResponseEntity<>(carReadOnlyDTO, HttpStatus.CREATED);

    }

    /**
     * Updates an existing car in the user's garage.
     *
     * @param loggedInUser the authenticated user
     * @param carId the ID of the car to update
     * @param carUpdateDTO the updated car data
     * @param bindingResult validation results
     * @return the updated car details
     * @throws AppObjectNotFoundException if the car is not found
     * @throws AppObjectInvalidArgumentException if the update fails due to invalid data
     */
    @Operation(summary = "Update an existing car in the user's garage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
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

    /**
     * Deletes a car from the authenticated user's garage.
     *
     * @param carId the ID of the car to delete
     * @param user the authenticated user
     * @return HTTP 204 NO_CONTENT on successful deletion
     * @throws AppObjectNotFoundException if the car is not found
     */
    @Operation(summary = "Delete a car from the user's garage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Car successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @DeleteMapping("/delete/{carId}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long carId, @AuthenticationPrincipal User user)
        throws AppObjectNotFoundException
    {
            garageService.deleteCar(user, carId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves car details by car ID.
     *
     * @param carId the ID of the car
     * @return car details
     * @throws AppObjectNotFoundException if the car is not found
     */
    @Operation(summary = "Retrieve a car by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car details retrieved successfully"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated"),
            @ApiResponse(responseCode = "404", description = "Car not found")
    })
    @GetMapping("/{carId}")
    public ResponseEntity<CarReadOnlyDTO> getCarById(@PathVariable Long carId) throws AppObjectNotFoundException {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new AppObjectNotFoundException("Car", "Car not found"));
        return ResponseEntity.ok(mapper.mapToCarReadOnlyDTO(car));
    }

    /**
     * Uploads an image for a specific car in the authenticated user's garage.
     * This endpoint allows users to upload an image file associated with a car they own.
     * The uploaded file is processed and linked to the corresponding car entity.
     * If the car is not found or an image already exists, appropriate exceptions are thrown.
     *
     * @param carId the ID of the car for which the image is being uploaded
     * @param file the image file to upload (multipart request)
     * @return ResponseEntity containing a message and the saved image filename
     * @throws AppObjectNotFoundException if the car with the specified ID is not found
     * @throws AppObjectInvalidArgumentException if the file is empty or invalid
     * @throws AppObjectAlreadyExistsException if an image already exists for the specified car
     */
    @Operation(summary = "Upload an image for a car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or request data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated"),
            @ApiResponse(responseCode = "404", description = "Car not found"),
            @ApiResponse(responseCode = "409", description = "Conflict - Image already exists")
    })
    @PostMapping("/{carId}/upload-image")
    public ResponseEntity<ResponseMessageDTO> uploadCarImage (
            @PathVariable Long carId,
            @RequestParam("file") MultipartFile file)
            throws AppObjectNotFoundException, AppObjectInvalidArgumentException, AppObjectAlreadyExistsException {

        // Call the attachment service to handle the file upload and associate it with the car
        Attachment attachment =  attachmentService.saveAttachment(carId, file);

        // Return a success response with metadata about the uploaded image
        return new ResponseEntity<>(new ResponseMessageDTO("Image", "Image uploaded successfully : "
                + attachment.getFilename()), HttpStatus.OK);
    }
}





