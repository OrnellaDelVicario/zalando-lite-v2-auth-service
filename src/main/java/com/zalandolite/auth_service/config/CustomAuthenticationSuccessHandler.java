package com.zalandolite.auth_service.config;

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

    private final JwtTokenProvider jwtTokenProvider;

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

        // Redirect the user. For testing purposes, we can redirect to a URL
        // that shows the token. In a real-world app, this would be a UI route.
        response.sendRedirect("/login-success?token=" + jwtToken);
    }
}