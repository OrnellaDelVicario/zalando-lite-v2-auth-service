package com.zalandolite.auth_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * Main security configuration class for the Auth Service.
 * This class configures OAuth2 login and protects internal endpoints.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    /**
     * Injects the custom authentication success handler.
     * @param customAuthenticationSuccessHandler The handler for successful logins.
     */
    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disables CSRF for REST API calls
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        // Protects the /internal/token endpoint, only authenticated users can access it.
                        .requestMatchers("/internal/**").authenticated()
                        // Allows all other requests without authentication.
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        // Specifies our custom handler to be executed after a successful login.
                        .successHandler(customAuthenticationSuccessHandler)
                );

        return http.build();
    }
}