package com.techlabs.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AgentRequestDto {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String phoneNumber;
    
    
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    private boolean isActive=true;
    
    private long city_id;
    
    private long state_id;
    


}