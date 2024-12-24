package gr.aueb.cf.carrentalapp.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import java.io.IOException;

/**
 * Custom handler for managing access denied exceptions in Spring Security.
 * This handler is triggered when a user attempts to access a resource they are not authorized for.
 * It returns a JSON response with a 403 Forbidden status and a custom error message.
 */
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * Handles access denied exception by returning a custom JSON response.
     * This method is invoked automatically by Spring Security when access is denied.
     *
     * @param request the HttpServletRequest representing the client's request
     * @param response the HttpServletResponse that will contain the error message
     * @param accessDeniedException the exception that triggered the access denied error
     * @throws IOException if an input or output error occurs during response writing
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException {

        // Set the response status and content type
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json;charset=UTF-8");

        // Write a custom JSON response with the collected information
        String jsonResponse = "{\"code\": \"userNotAuthorized\", \"description\": \"User is not allowed to visit this route.\"}";
        response.getWriter().write(jsonResponse);
    }
}
