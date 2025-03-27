package com.productsAPI.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
@Schema(description = "Model that represents a product.")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Schema(description = "Unique product ID", example = "1")
    @Column(name = "id")
    private Long id;

    @Schema(description = "Product name", example = "T-shirt")
    @Column(name = "name")
    private String name;

    @Schema(description = "Product price", example = "29.99")
    @Column(name = "price")
    private Double price;

    @Schema(description = "Quantity of the product available in stock", example = "39")
    @Column(name = "quantity")
    private Integer quantity;
}
