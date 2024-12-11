package com.hungerhub.test.serviceimpl;

import com.hungerhub.dto.OrderedProductsResponseDto;
import com.hungerhub.dto.ReviewDto;
import com.hungerhub.entity.CartItems;
import com.hungerhub.entity.Order;
import com.hungerhub.entity.Product;
import com.hungerhub.entity.User;
import com.hungerhub.repository.OrderRepository;
import com.hungerhub.repository.ProductRepository;
import com.hungerhub.repository.ReviewRepository;
import com.hungerhub.repository.UserRepository;
import com.hungerhub.services.customer.review.ReviewServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.when;

public class ReviewServiceImplTest {

    @InjectMocks
    private ReviewServiceImpl reviewService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testgetOrderedProductsDetailsByOrderId(){
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        order.setAmount(100L);
        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setPrice(50L);
        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setPrice(60L);
        CartItems cartItem1 = new CartItems();
        cartItem1.setProduct(product1);
        cartItem1.setPrice(product1.getPrice());
        cartItem1.setQuantity(2L);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        cartItem1.setUser(user);
        order.setUser(user);
        CartItems cartItem2 = new CartItems();
        cartItem2.setProduct(product2);
        cartItem2.setPrice(product2.getPrice());
        cartItem2.setQuantity(1L);
        cartItem1.setUser(user);
        cartItem2.setUser(user);
        order.setCartItems(Arrays.asList(cartItem1, cartItem2));
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        OrderedProductsResponseDto result = reviewService.getOrderedProductsDetailsByOrderId(orderId);
    }

    @Test
    public void testgiveReview() throws IOException {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setUserId(1L);
        reviewDto.setProductId(1L);
        reviewDto.setRating(5L);
        reviewDto.setDescription("Great product");
        MultipartFile imgFile = new MockMultipartFile(
                "test.jpg",   // file name
                "test.jpg",   // original file name
                "image/jpeg", // content type
                "Some data".getBytes(StandardCharsets.UTF_8) // file content
        );
        reviewDto.setImg(imgFile);
        Product product = new Product();
        product.setId(1L);
        User user = new User();
        user.setId(1L);
        when(productRepository.findById(reviewDto.getProductId())).thenReturn(Optional.of(product));
        when(userRepository.findById(reviewDto.getUserId())).thenReturn(Optional.of(user));
        when(reviewRepository.save(Mockito.any())).thenAnswer(invocation -> invocation.getArgument(0));
        ReviewDto result = reviewService.giveReview(reviewDto);
    }
}
