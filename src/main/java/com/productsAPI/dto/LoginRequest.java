package com.productsAPI.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "The email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "The password cannot be blank")
    private String password;
}
