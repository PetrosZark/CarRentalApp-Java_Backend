package gr.aueb.cf.carrentalapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

/**
 * Custom entry point to handle unauthorized access attempts in Spring Security.
 * This is triggered when an unauthenticated user tries to access a protected resource.
 * Returns a 401 Unauthorized response with a custom JSON error message.
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized requests by returning a custom 401 error response.
     * This method is called automatically when authentication is required but not provided.
     *
     * @param request       the HttpServletRequest object representing the client's request
     * @param response      the HttpServletResponse object used to return the error response
     * @param authException the exception representing the authentication error
     * @throws IOException      if an input/output error occurs during response writing
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        // Set the response status to 401 Unauthorized
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Write a custom error message or JSON structure
        String json = "{\"code\": \"userNotAuthenticated\", \"description\": \"User not authenticated\"}";
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(json);
    }
}
