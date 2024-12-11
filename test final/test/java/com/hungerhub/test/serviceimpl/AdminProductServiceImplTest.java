package com.hungerhub.test.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.hungerhub.dto.ProductDto;
import com.hungerhub.entity.Category;
import com.hungerhub.entity.Product;
import com.hungerhub.repository.CategoryRepository;
import com.hungerhub.repository.ProductRepository;
import com.hungerhub.services.admin.adminproduct.AdminProductServiceImpl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class AdminProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private AdminProductServiceImpl adminProductService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testaddProduct() throws IOException {
        ProductDto productDto = new ProductDto();
        productDto.setName("Test Product");
        productDto.setDescription("Test Description");
        productDto.setPrice(100L);
        productDto.setCategoryId(1L);
        Category category = new Category();
        category.setId(1L);
        Product product = new Product();
        product.setCategory(category);
        product.setId(12L);
        MultipartFile imgFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                "image/jpeg",
                "Some data".getBytes(StandardCharsets.UTF_8) // file content
        );
        productDto.setImg(imgFile);
        when(categoryRepository.findById(productDto.getCategoryId())).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);
        ProductDto result = adminProductService.addProduct(productDto);
        assertNotNull(result);
    }

    @Test
    public void testgetAllProducts(){
        Category category = new Category();
        category.setId(1L);
        Product product1 = new Product();
        product1.setId(1L);
        product1.setCategory(category);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setCategory(category);
        List<Product> productList = List.of(product1, product2);
        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDto> result = adminProductService.getAllProducts();
        assertNotNull(result);
        assertEquals(productList.size(), result.size());
    }


    @Test
    public void testgetAllProductsbyName(){
        String name ="test name";
        Category category = new Category();
        category.setId(1L);
        Product product1 = new Product();
        product1.setId(1L);
        product1.setCategory(category);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setCategory(category);
        List<Product> productList = List.of(product1, product2);
        when(productRepository.findAllByNameContaining(anyString())).thenReturn(productList);
        List<ProductDto> result = adminProductService.getAllProductByName(name);
        assertNotNull(result);
        assertEquals(productList.size(), result.size());
    }

    @Test
    public void testDeleteProduct_ProductExists() {
        long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        boolean result = adminProductService.deleteProduct(productId);
        assertTrue(result);
    }

    @Test
    public void testupdateProduct() throws IOException{
        long productId = 1L;
        long categoryId = 1L;
        ProductDto productDto = new ProductDto();
        productDto.setName("Updated Product");
        productDto.setPrice(100L);
        productDto.setDescription("Updated Description");
        productDto.setCategoryId(categoryId);
        MultipartFile imgFile = new MockMultipartFile(
                "test.jpg",
                "test.jpg",
                "image/jpeg",
                "Some data".getBytes(StandardCharsets.UTF_8)
        );
        productDto.setImg(imgFile);
        Product existingProduct = new Product();
        existingProduct.setId(productId);
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingProduct.setCategory(existingCategory);
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(productRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        ProductDto result = adminProductService.updateProduct(productId, productDto);
    }
}
