package com.techlabs.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.JWTAuthResponse;
import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.service.AuthService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    // Build Login REST API
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
    	logger.error("In Login method");
        String token = authService.login(loginDto);
        System.out.println(loginDto);
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
    	logger.trace("in register method using trace ");
    	logger.error("In Register method "+registerDto.isAdmin());
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}