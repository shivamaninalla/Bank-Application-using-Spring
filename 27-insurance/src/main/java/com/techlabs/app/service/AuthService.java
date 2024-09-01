package com.techlabs.app.service;

import com.techlabs.app.dto.LoginDto;
import com.techlabs.app.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
