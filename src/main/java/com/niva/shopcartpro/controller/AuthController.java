package com.niva.shopcartpro.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.niva.shopcartpro.dto.LoginRequestDTO;
import com.niva.shopcartpro.model.User;
import com.niva.shopcartpro.repository.UserRepository;
import com.niva.shopcartpro.service.JwtService;

/* this class is created to create a new endpoint for generating JWT token  */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthController(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            JwtService jwtService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
            )
        );

        User user = userRepository.findAll()
                .stream()
                .filter(u -> u.getEmail().equals(request.getEmail()))
                .findFirst()
                .orElseThrow(() ->
                    new RuntimeException("User not found"));

        return jwtService.generateToken(
                user.getEmail(),
                user.getRole()
        );
    }
}