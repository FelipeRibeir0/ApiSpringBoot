package com.productsAPI.dto;

import com.productsAPI.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDTO {

    @NotBlank(message = "The user's name cannot be empty")
    private String name;

    @NotBlank(message = "The email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "The password cannot be empty")
    @Size(min = 8, message = "The password must be at least 8 characters long")
    private String password;

    @NotNull(message = "The user's role must be specified")
    private User.Role role;
}
