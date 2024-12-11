//package com.hungerHub.ecom;
//
//public class AdminProductControllerTest {
//
//}
package com.hungerHub.ecom;

import com.ecom.controller.admin.AdminProductController;
//import com.ecom.dto.FAQDto;
import com.ecom.dto.ProductDto;
import com.ecom.services.admin.adminproduct.AdminProductService;
//import com.ecom.services.admin.faq.FAQService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class AdminProductControllerTest {

    @Mock
    private AdminProductService adminProductService;
//    @Mock
//    private FAQService faqService;
    @InjectMocks
    private AdminProductController adminProductController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddProduct() throws IOException {
        ProductDto productDto = new ProductDto();
        when(adminProductService.addProduct(productDto)).thenReturn(productDto);
        ResponseEntity<ProductDto> responseEntity = adminProductController.addProduct(productDto);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(productDto, responseEntity.getBody());
    }

    @Test
    void testGetAllProducts() {
        List<ProductDto> productDtos = new ArrayList<>();
        when(adminProductService.getAllProducts()).thenReturn(productDtos);
        ResponseEntity<List<ProductDto>> responseEntity = adminProductController.getAllProducts();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productDtos, responseEntity.getBody());
    }

    @Test
    void testGetAllProductByName() {
        String name = "Product";
        List<ProductDto> productDtos = new ArrayList<>();
        when(adminProductService.getAllProductByName(name)).thenReturn(productDtos);
        ResponseEntity<List<ProductDto>> responseEntity = adminProductController.getAllProductByName(name);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productDtos, responseEntity.getBody());
    }

    @Test
    void testDeleteProduct() {
        Long productId = 1L;
        when(adminProductService.deleteProduct(productId)).thenReturn(true);
        ResponseEntity<Void> responseEntityFound = adminProductController.deleteProduct(productId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntityFound.getStatusCode());
        when(adminProductService.deleteProduct(productId)).thenReturn(false);
        ResponseEntity<Void> responseEntityNotFound = adminProductController.deleteProduct(productId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntityNotFound.getStatusCode());
    }

//    @Test
//    void testPostFAQ() {
//        Long productId = 1L;
//        FAQDto faqDto = new FAQDto();
//        when(faqService.postFAQ(productId, faqDto)).thenReturn(faqDto);
//        ResponseEntity<FAQDto> responseEntity = adminProductController.postFAQ(productId, faqDto);
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//        assertEquals(faqDto, responseEntity.getBody());
//    }

    @Test
    void testGetProductByIdWhenExists() {
        Long productId = 1L;
        ProductDto productDto = new ProductDto();
        when(adminProductService.getProductById(productId)).thenReturn(productDto);
        ResponseEntity<ProductDto> responseEntity = adminProductController.getProductById(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productDto, responseEntity.getBody());
    }

    @Test
    void testGetProductByIdWhenNotExists() {
        Long productId = 1L;
        when(adminProductService.getProductById(productId)).thenReturn(null);
        ResponseEntity<ProductDto> responseEntity = adminProductController.getProductById(productId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void testUpdateProduct() throws IOException {
        Long productId = 1L;
        ProductDto productDto = new ProductDto();
        when(adminProductService.updateProduct(productId, productDto)).thenReturn(productDto);
        ResponseEntity<ProductDto> responseEntity = adminProductController.updateProduct(productId, productDto);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(productDto, responseEntity.getBody());
    }
}
