package com.techlabs.app.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.LoginResponseDto;
import com.techlabs.app.dto.RegisterDto;
import com.techlabs.app.entity.Admin;
import com.techlabs.app.entity.Customer;
import com.techlabs.app.entity.Role;
import com.techlabs.app.entity.User;
import com.techlabs.app.exception.BankApiException;
import com.techlabs.app.repository.AdminRepository;
import com.techlabs.app.repository.RoleRepository;
import com.techlabs.app.repository.UserRepository;
import com.techlabs.app.security.JwtTokenProvider;

@Service
public class AuthServiceImpl implements AuthService {
	
	Logger logger=LoggerFactory.getLogger(AuthServiceImpl.class);

	private AuthenticationManager authenticationManager;
	private UserRepository userRepository;
	private AdminRepository adminRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder passwordEncoder;
	private JwtTokenProvider jwtTokenProvider;

	public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
			RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,AdminRepository adminRepository) {
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtTokenProvider = jwtTokenProvider;
		this.adminRepository=adminRepository;
	}

//	@Override
//	public String login(LoginDto loginDto) {
//
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
//
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//
//		String token = jwtTokenProvider.generateToken(authentication);
//
//		return token;
//	}

	@Override
	public String register(RegisterDto registerDto) {

	    if (registerDto.isAdmin()) {
	        if (adminRepository.existsByEmail(registerDto.getEmail())) {
	            throw new BankApiException(HttpStatus.BAD_REQUEST, "Email already exists for admin!");
	        }

	        Admin admin = new Admin();
	        admin.setEmail(registerDto.getEmail());
	        admin.setPassword(passwordEncoder.encode(registerDto.getPassword()));
	        admin.setName(registerDto.getName());
	        adminRepository.save(admin);

	        return "Admin registered successfully!";
	    } else {
	        if (userRepository.existsByEmail(registerDto.getEmail())) {
	            throw new BankApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
	        }

	        User user = new User();
	        user.setEmail(registerDto.getEmail());
	        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

	        Set<Role> roles = new HashSet<>();
	        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
	                new BankApiException(HttpStatus.BAD_REQUEST, "User role not set up in the database"));
	        
	        roles.add(role);
	        user.setRoles(roles);

	        userRepository.save(user);

	        return "User registered successfully!";
	    }
	}
	
	
	@Override
	public LoginResponseDto login(LoginDto loginDto) {

	    Authentication authentication = authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String token = jwtTokenProvider.generateToken(authentication);

	    // Fetch user details
	    User user = userRepository.findByEmail(loginDto.getEmail()).orElse(null);
	    Admin admin = adminRepository.findByEmail(loginDto.getEmail()).orElse(null);

	    Set<String> roles;

	    if (user != null) {
	        roles = user.getRoles().stream()
	            .map(Role::getName)
	            .collect(Collectors.toSet());
	    } else if (admin != null) {
	        roles = Set.of("ROLE_ADMIN");
	    } else {
	        throw new BankApiException(HttpStatus.UNAUTHORIZED, "User not found");
	    }

	    // Create response DTO without the token
	    LoginResponseDto loginResponseDto = new LoginResponseDto();
	    loginResponseDto.setEmail(loginDto.getEmail());
	    loginResponseDto.setRoles(roles);

	    return loginResponseDto;
	}
	
	@Override
	public Map<String, Object> getUserByEmail(String email) {
	    User user = userRepository.findByEmail(email).orElse(null);
	    
	    if (user == null) {
	        return null;
	    }
	    String fullName="User";
	    Customer customer = user.getCustomer();
	    if(customer!=null) {
	    	fullName=user.getCustomer().getFirstName() + " " + user.getCustomer().getLastName();
	    }
	    Map<String, Object> userDetails = new HashMap<>();
	    userDetails.put("userId", user.getId());
	    userDetails.put("email", user.getEmail());
	    userDetails.put("userName", fullName);
	    
	    return userDetails;
	}
	
	

}