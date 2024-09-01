package com.techlabs.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.LoginResponseDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.security.JwtTokenProvider;
import com.techlabs.app.service.AuthService;


@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", exposedHeaders = HttpHeaders.AUTHORIZATION)
public class AuthController {

	private AuthService authService;
	private JwtTokenProvider jwtTokenProvider;
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	public AuthController(AuthService authService,JwtTokenProvider jwtTokenProvider) {
		this.authService = authService;
		this.jwtTokenProvider=jwtTokenProvider;
	}

    // Build Login REST API
//    @PostMapping(value = {"/login", "/signin"})
//    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
//    	logger.error("In Login method");
//        String token = authService.login(loginDto);
//        System.out.println(loginDto);
//        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
//        jwtAuthResponse.setAccessToken(token);
//
//        return ResponseEntity.ok(jwtAuthResponse);
//    }

    // Build Register REST API
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
    	logger.trace("in register method using trace ");
    	logger.error("In Register method "+registerDto.isAdmin());
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @PostMapping(value = { "/login", "/signin" })
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto) {
	    LoginResponseDto loginResponseDto = authService.login(loginDto);

	    String token = jwtTokenProvider.generateToken(SecurityContextHolder.getContext().getAuthentication());

	    return ResponseEntity.ok()
	            .header("Authorization", token)
	            .body(loginResponseDto);
	}
    
    @GetMapping("/verifyAdmin")
    public ResponseEntity<Boolean> verifyAdmin(@RequestParam(name = "auth") String token) {
        // Remove "Bearer " prefix if present
		if(token=="") {
			return ResponseEntity.ok(false);
		}
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if (jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            if (authentication != null && authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }
	
	@GetMapping("/verifyUser")
    public ResponseEntity<Boolean> verifyUser(@RequestParam(name = "auth") String token) {
        // Remove "Bearer " prefix if present
		if(token=="") {
			return ResponseEntity.ok(false);
		}
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        if (jwtTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            if (authentication != null && authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"))) {
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }
	
	@GetMapping("getCurrentUser")
	public Map<String,Object> getUserByEmail(@RequestParam(name = "email")String email) {
		return authService.getUserByEmail(email);
	}
	
	
	
}