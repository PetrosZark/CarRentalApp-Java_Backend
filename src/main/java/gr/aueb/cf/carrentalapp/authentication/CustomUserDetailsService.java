package gr.aueb.cf.carrentalapp.authentication;

import gr.aueb.cf.carrentalapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service that implements Spring Security's UserDetailsService.
 * This service is responsible for loading user-specific data during authentication.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Loads user details by username during the authentication process.
     *
     * @param username The username of the user to be loaded.
     * @return UserDetails object containing the user's data.
     * @throws UsernameNotFoundException if the user with the given username is not found.
     */@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Fetch user by username from the database
        return userRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User with username: " + username + " not found"));
    }
}
