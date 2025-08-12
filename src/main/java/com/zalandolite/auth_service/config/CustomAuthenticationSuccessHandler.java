package com.zalandolite.auth_service.config; // Keep your package

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Custom handler for successful OAuth2 authentication.
 * It generates a JWT token and redirects the user after a successful login.
 */
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider; // This is your JwtService/JwtTokenProvider

    @Autowired
    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
        // The user ID is usually stored in the "sub" attribute for Google.
        String userId = oauth2User.getAttribute("sub");

        // Generates a JWT token for the user.
        String jwtToken = jwtTokenProvider.generateToken(userId);

        // *** IMPORTANT: Print the token to console logs for debugging, NOT to the browser URL ***
        System.out.println("DEBUG: Generated JWT for user " + userId + " (for internal use): " + jwtToken);

        // Redirect the user to a success page that IS MAPPED in your WelcomeController
        // and DO NOT include the token in the URL for security reasons.
        response.sendRedirect("/user-info");
    }
}