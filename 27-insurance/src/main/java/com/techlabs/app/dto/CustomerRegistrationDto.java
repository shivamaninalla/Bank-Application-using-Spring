package com.techlabs.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CustomerRegistrationDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Age is mandatory")
    private Integer age;

    @NotBlank(message = "Phone number is mandatory")
    @Size(min = 10, max = 10, message = "Phone number should be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Address is mandatory")
    private String address;

    @NotNull(message = "Pincode is mandatory")
    private long pincode;

    @NotNull(message = "State ID is mandatory")
    private long stateId;

    @NotNull(message = "Agent ID is mandatory")
    private long agentId;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;

    @NotBlank(message = "Username is mandatory")
    private String username;

    @NotBlank(message = "Password is mandatory")
    private String password;

    // Getters and Setters
}