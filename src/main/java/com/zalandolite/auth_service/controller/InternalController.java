package com.zalandolite.auth_service.controller;

import com.zalandolite.auth_service.config.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
public class InternalController {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public InternalController(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/token")
    public String getTokenForUser(@RequestParam String userId) {
        return jwtTokenProvider.generateToken(userId);
    }
}