package com.zalandolite.auth_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

@RestController
public class WelcomeController {

    /**
     * Handles GET requests to the root path ("/").
     * This serves as a basic entry point or default page for the Auth Service.
     * Users might land here if they access the service directly or if a redirect
     * defaults to the root.
     *
     * @return An HTML welcome message with a clickable login link.
     */
    @GetMapping("/")
    public String home() {
        // Return HTML content with a clickable link
        return "<h1>Welcome to ZalandoLite+ v2 Auth Service!</h1>" +
                "<p>Please login via: <a href=\"/oauth2/authorization/google\">/oauth2/authorization/google</a></p>";
    }

    /**
     * Handles GET requests to the "/user-info" path.
     * This is intended as the landing page after a successful Google OAuth2 login.
     * It demonstrates how to access details of the authenticated user.
     * The @AuthenticationPrincipal annotation injects the OAuth2User object,
     * which contains user attributes provided by Google.
     *
     * @param oauth2User The authenticated OAuth2User object.
     * @return A message displaying successful login and basic user information.
     */
    @GetMapping("/user-info")
    public String userInfo(@AuthenticationPrincipal OAuth2User oauth2User) {
        if (oauth2User != null) {
            String name = oauth2User.getAttribute("name");
            String email = oauth2User.getAttribute("email");
            String userId = oauth2User.getAttribute("sub"); // Google's unique ID for the user

            return "Login successful! Welcome, " + name + "!<br>" +
                    "Your email is: " + email + "<br>" +
                    "Your Google ID is: " + userId + "<br>" +
                    "You can now interact with other microservices.";
        }
        return "Login successful, but user information could not be retrieved.";
    }

    /**
     * Handles GET requests to the "/error" path.
     * This method serves as a fallback error page for any unhandled exceptions
     * or 404s that Spring Boot might redirect to by default.
     *
     * @return A generic error message.
     */
    @GetMapping("/error")
    public String handleError() {
        return "An unexpected error occurred. Please try again.";
    }
}