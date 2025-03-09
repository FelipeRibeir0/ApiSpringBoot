package com.productsAPI.service;

import com.productsAPI.dto.ProductDTO;
import com.productsAPI.model.Product;
import com.productsAPI.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(ProductDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setQuantity(dto.getQuantity());
        product.setPrice(dto.getPrice());
        return productRepository.save(product);
    }

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, ProductDTO dto) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(dto.getName());
            product.setQuantity(dto.getQuantity());
            product.setPrice(dto.getPrice());
            return productRepository.save(product);
        }
        return null;
    }

    public Product partialUpdate(Long id, ProductDTO dto) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            if (dto.getName() != null) product.setName(dto.getName());
            if (dto.getQuantity() != null) product.setQuantity(dto.getQuantity());
            if (dto.getPrice() != null) product.setPrice(dto.getPrice());
            return productRepository.save(product);
        }
        return null;
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
