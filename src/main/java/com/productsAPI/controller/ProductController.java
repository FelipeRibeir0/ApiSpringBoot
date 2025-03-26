package com.productsAPI.controller;

import com.productsAPI.dto.ProductDTO;
import com.productsAPI.model.Product;
import com.productsAPI.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Create a new product", description = "Creates a new product with name, quantity, and price. Returns the created product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid data provided for the product"),
            @ApiResponse(responseCode = "409", description = "Product already exists with the provided data")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(headers = "X-API-Version=v1")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDTO dto) {
        Product savedProduct = productService.saveProduct(dto);
        return ResponseEntity.status(201).body(savedProduct);
    }

    @Operation(summary = "List all products", description = "Returns a list of all available products.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product list successfully returned"),
            @ApiResponse(responseCode = "404", description = "No products found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @GetMapping(headers = "X-API-Version=v1")
    public ResponseEntity<List<Product>> listProducts() {
        List<Product> products = productService.listAll();
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Find products by name", description = "Returns a list of products that match the provided name, case-insensitive.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Products successfully found"),
            @ApiResponse(responseCode = "404", description = "No products found with the provided name")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @GetMapping(value = "/search", headers = "X-API-Version=v1")
    public ResponseEntity<List<Product>> findProductsByName(@RequestParam String name) {
        List<Product> products = productService.searchByName(name);
        if (products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products);
        }

    @Operation(summary = "Find a product by ID", description = "Returns a specific product based on the provided ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully found"),
            @ApiResponse(responseCode = "404", description = "Product not found with the provided ID")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'CLIENT')")
    @GetMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Product> findProductById(@PathVariable Long id) {
        Optional<Product> product = productService.findById(id);
        return product.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Update all product attributes", description = "Replaces all attributes of an existing product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product successfully updated"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        Product updatedProduct = productService.updateProduct(id, dto);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Partially update a product", description = "Updates only some attributes of a specific product.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product partially updated successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Product> partiallyUpdateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        Product product = productService.partialUpdate(id, dto);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete a product", description = "Deletes the specified product by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
