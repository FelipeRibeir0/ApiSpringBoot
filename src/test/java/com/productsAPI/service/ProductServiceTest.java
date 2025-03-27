package com.productsAPI.service;

import com.productsAPI.dto.ProductDTO;
import com.productsAPI.model.Product;
import com.productsAPI.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void saveProduct_ShouldReturnSavedProduct_WhenValidDTO() {
        ProductDTO dto = new ProductDTO("Notebook", 10, 4500.00);
        Product savedProduct = new Product(1L, "Notebook", 4500.00, 10);

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.saveProduct(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Notebook", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void listAll_ShouldReturnAllProducts() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Notebook", 4500.00, 10),
                new Product(2L, "Mouse", 120.00, 50)
        );
        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.listAll();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void searchByName_ShouldReturnFilteredProducts() {
        String searchTerm = "note";
        List<Product> products = List.of(
                new Product(1L, "Notebook", 4500.00, 10)
        );
        when(productRepository.findByNameContainingIgnoreCase(searchTerm)).thenReturn(products);

        List<Product> result = productService.searchByName(searchTerm);

        assertEquals(1, result.size());
        assertEquals("Notebook", result.getFirst().getName());
        verify(productRepository, times(1)).findByNameContainingIgnoreCase(searchTerm);
    }

    @Test
    void findById_ShouldReturnProduct_WhenIdExists() {
        Long id = 1L;
        Product product = new Product(id, "Notebook", 4500.00, 10);
        when(productRepository.findById(id)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.findById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void findById_ShouldReturnEmpty_WhenIdDoesNotExist() {
        Long id = 999L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Optional<Product> result = productService.findById(id);

        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findById(id);
    }

    @Test
    void updateProduct_ShouldReturnUpdatedProduct_WhenIdExists() {
        Long id = 1L;
        ProductDTO dto = new ProductDTO("Notebook Updated", 15, 4000.00);
        Product existingProduct = new Product(id, "Notebook",4500.00,  10);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.updateProduct(id, dto);

        assertEquals("Notebook Updated", result.getName());
        assertEquals(15, result.getQuantity());
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void updateProduct_ShouldReturnNull_WhenIdDoesNotExist() {
        Long id = 999L;
        ProductDTO dto = new ProductDTO("Notebook", 10, 4500.00);
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        Product result = productService.updateProduct(id, dto);

        assertNull(result);
        verify(productRepository, never()).save(any());
    }

    @Test
    void partialUpdate_ShouldUpdateOnlyName_WhenOnlyNameIsProvided() {
        Long id = 1L;
        ProductDTO dto = new ProductDTO("Notebook Updated", null, null);
        Product existingProduct = new Product(id, "Notebook",4500.00,  10);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(existingProduct)).thenReturn(existingProduct);

        Product result = productService.partialUpdate(id, dto);

        assertEquals("Notebook Updated", result.getName()); // Campo alterado
        assertEquals(10, result.getQuantity()); // Campo não alterado
        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    void deleteProduct_ShouldReturnTrue_WhenIdExists() {
        Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(true);

        boolean result = productService.deleteProduct(id);

        assertTrue(result);
        verify(productRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteProduct_ShouldReturnFalse_WhenIdDoesNotExist() {
        Long id = 999L;
        when(productRepository.existsById(id)).thenReturn(false);

        boolean result = productService.deleteProduct(id);

        assertFalse(result);
        verify(productRepository, never()).deleteById(any());
    }
}