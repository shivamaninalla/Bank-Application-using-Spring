package com.techlabs.app.service;

import java.util.Map;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.LoginResponseDto;
import com.techlabs.app.dto.RegisterDto;

public interface AuthService {
    LoginResponseDto login(LoginDto loginDto);

    String register(RegisterDto registerDto);

	Map<String, Object> getUserByEmail(String email);
}