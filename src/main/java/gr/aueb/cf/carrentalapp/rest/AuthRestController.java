package gr.aueb.cf.carrentalapp.rest;

import gr.aueb.cf.carrentalapp.authentication.AuthenticationService;
import gr.aueb.cf.carrentalapp.core.exceptions.*;
import gr.aueb.cf.carrentalapp.dto.AuthenticationRequestDTO;
import gr.aueb.cf.carrentalapp.dto.AuthenticationResponseDTO;
import gr.aueb.cf.carrentalapp.dto.UserInsertDTO;
import gr.aueb.cf.carrentalapp.dto.UserReadOnlyDTO;
import gr.aueb.cf.carrentalapp.service.UserService;
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


@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class AuthRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthRestController.class);
    private final AuthenticationService authenticationService;
    private final UserService userService;


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate
            (@Valid @RequestBody AuthenticationRequestDTO authenticationRequestDTO) throws AppObjectNotAuthorizedException {
        AuthenticationResponseDTO authenticationResponseDTO =
                authenticationService.authenticate(authenticationRequestDTO);
        LOGGER.info("User authenticated.");
        return new ResponseEntity<>(authenticationResponseDTO, HttpStatus.OK);
    }

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
