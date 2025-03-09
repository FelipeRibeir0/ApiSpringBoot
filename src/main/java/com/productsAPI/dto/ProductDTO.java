package com.productsAPI.dto;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductDTO {

    @NotBlank(message = "The product name cannot be empty")
    private String name;

    @NotNull(message = "The product quantity cannot be null")
    @Positive(message = "The quantity must be greater than zero")
    private Integer quantity;

    @NotNull(message = "The product price cannot be null")
    @Positive(message = "The price must be greater than zero")
    private Double price;
}
