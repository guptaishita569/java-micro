package com.hungerhub.test.controller.customer;

import com.hungerhub.controller.customer.CustomerProductController;
import com.hungerhub.dto.ProductDetailDto;
import com.hungerhub.dto.ProductDto;
import com.hungerhub.services.customer.CustomerProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerProductControllerTest {

    @InjectMocks
    private CustomerProductController customerProductController;
    @Mock
    private CustomerProductService customerProductService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        when(customerProductService.getAllProducts()).thenReturn(productDtos);
        ResponseEntity<List<ProductDto>> result = customerProductController.getAllProducts();
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(productDtos, result.getBody());
        verify(customerProductService, times(1)).getAllProducts();
    }

    @Test
    void testGetAllProductByName() {
        String name = "Product";
        List<ProductDto> productDtos = new ArrayList<>();
        when(customerProductService.searchProductByTitle(name)).thenReturn(productDtos);
        ResponseEntity<List<ProductDto>> result = customerProductController.getAllProductByName(name);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(productDtos, result.getBody());
        verify(customerProductService, times(1)).searchProductByTitle(name);
    }

    @Test
    void testGetProductDetailByIdFound() {
        Long productId = 1L;
        ProductDetailDto productDetailDto = new ProductDetailDto();
        when(customerProductService.getProductDetailById(productId)).thenReturn(productDetailDto);
        ResponseEntity<ProductDetailDto> result = customerProductController.getProductDetailById(productId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(productDetailDto, result.getBody());
        verify(customerProductService, times(1)).getProductDetailById(productId);
    }

    @Test
    void testGetProductDetailByIdNotFound() {
        Long productId = 1L;
        when(customerProductService.getProductDetailById(productId)).thenReturn(null);
        ResponseEntity<ProductDetailDto> result = customerProductController.getProductDetailById(productId);
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        assertNull(result.getBody());
        verify(customerProductService, times(1)).getProductDetailById(productId);
    }
}
