package gr.aueb.cf.carrentalapp.rest;

import gr.aueb.cf.carrentalapp.authentication.AuthenticationService;
import gr.aueb.cf.carrentalapp.core.exceptions.*;
import gr.aueb.cf.carrentalapp.dto.AuthenticationRequestDTO;
import gr.aueb.cf.carrentalapp.dto.AuthenticationResponseDTO;
import gr.aueb.cf.carrentalapp.dto.UserInsertDTO;
import gr.aueb.cf.carrentalapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.carrentalapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for handling authentication and user registration.
 * <p>
 * Provides endpoints for user authentication and new user registration.
 * </p>
 */
@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AuthRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);
    private final AuthenticationService authenticationService;
    private final UserService userService;


    /**
     * Authenticates a user and returns a JWT token upon successful login.
     *
     * @param dto the DTO containing username and password
     * @return ResponseEntity containing the authentication response with JWT token
     * @throws AppObjectNotAuthorizedException if the credentials are invalid
     */
    @Operation(summary = "Authenticate user and generate JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User successfully authenticated"),
            @ApiResponse(responseCode = "403", description = "Invalid credentials or user not authorized")
    })
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate
            (@Valid @RequestBody AuthenticationRequestDTO dto) throws AppObjectNotAuthorizedException {
        AuthenticationResponseDTO authenticationResponseDTO = authenticationService.authenticate(dto);
        LOGGER.info("User authenticated.");
        return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
    }

    /**
     * Registers a new user in the system.
     *
     * @param dto the UserInsertDTO containing user details for registration
     * @param bindingResult contains validation errors if any
     * @return ResponseEntity containing the registered user's read-only details
     * @throws AppObjectAlreadyExistsException if the user already exists
     * @throws ValidationException if the provided data is invalid
     */
    @Operation(summary = "Register a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Validation error"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("/register")
    public ResponseEntity<UserReadOnlyDTO> saveUser (@Valid @RequestBody UserInsertDTO dto, BindingResult bindingResult)
            throws AppObjectAlreadyExistsException, ValidationException {
        LOGGER.info("Received request to register user: {}", dto);

        if (bindingResult.hasErrors()) {
            LOGGER.error("Validation errors: {}", bindingResult.getAllErrors());
            throw new ValidationException(bindingResult);
        }

        try {
            UserReadOnlyDTO userReadOnlyDTO = userService.saveUser(dto);
            LOGGER.info("User registered.");
            return new ResponseEntity<>(userReadOnlyDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            LOGGER.error("Error during user registration", e);
            throw e;
        }
    }
}
