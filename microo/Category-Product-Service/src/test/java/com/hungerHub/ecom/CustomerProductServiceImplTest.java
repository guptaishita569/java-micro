//package com.hungerHub.ecom;
//
//public class CustomerProductServiceImplTest {
//
//}
package com.hungerHub.ecom;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecom.dto.ProductDetailDto;
import com.ecom.dto.ProductDto;
import com.ecom.entity.Category;
import com.ecom.entity.FAQ;
import com.ecom.entity.Product;
import com.ecom.entity.Review;
//import com.ecom.entity.User;
import com.ecom.repository.FAQRepository;
import com.ecom.repository.ProductRepository;
import com.ecom.repository.ReviewRepository;
import com.ecom.services.customer.CustomerProductServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CustomerProductServiceImplTest {

    @Mock
    private FAQRepository faqRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private CustomerProductServiceImpl customerProductService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testgetAllProducts(){
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        Category category = new Category();
        category.setName("test");
        category.setId(12L);
        product1.setCategory(category);
        product2.setCategory(category);
        List<Product> productList = Arrays.asList(product1, product2);
        when(productRepository.findAll()).thenReturn(productList);
        List<ProductDto> result = customerProductService.getAllProducts();
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    public void testsearchProductByTitle(){
        Product product1 = new Product();
        product1.setId(1L);
        Product product2 = new Product();
        product2.setId(2L);
        Category category = new Category();
        category.setName("test");
        category.setId(12L);
        product1.setCategory(category);
        product2.setCategory(category);
        List<Product> productList = Arrays.asList(product1, product2);
        when(productRepository.findAllByNameContaining("keyword")).thenReturn(productList);
        List<ProductDto> result = customerProductService.searchProductByTitle("keyword");
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }
    @Test
    public void testgetProductDetailById(){
        Product product = new Product();
        product.setId(1L);
        FAQ faq = new FAQ();
        faq.setId(1L);
        faq.setProduct(product);
        Review review = new Review();
        review.setId(1L);
        review.setProduct(product);
//        User user = new User();
//        user.setId(12L);
//        user.setName("test");
//        review.setUser(user);
        Category category = new Category();
        category.setName("test");
        category.setId(12L);
        product.setCategory(category);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(faqRepository.findAllByProductId(1L)).thenReturn(Arrays.asList(faq));
        when(reviewRepository.findAllByProductId(1L)).thenReturn(Arrays.asList(review));
        ProductDetailDto result = customerProductService.getProductDetailById(1L);
        assertEquals(1L, result.getProductDto().getId());
        assertEquals(1, result.getFaqDtoList().size());
        assertEquals(1, result.getReviewDtoList().size());
    }
}
