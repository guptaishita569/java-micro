package com.hungerhub.test.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.hungerhub.dto.AddProductInCartDto;
import com.hungerhub.dto.OrderDto;
import com.hungerhub.dto.PlaceOrderDto;
import com.hungerhub.entity.CartItems;
import com.hungerhub.entity.Coupon;
import com.hungerhub.entity.Order;
import com.hungerhub.entity.Product;
import com.hungerhub.entity.User;
import com.hungerhub.enums.OrderStatus;
import com.hungerhub.repository.CartItemsRepository;
import com.hungerhub.repository.CouponRepository;
import com.hungerhub.repository.OrderRepository;
import com.hungerhub.repository.ProductRepository;
import com.hungerhub.repository.UserRepository;
import com.hungerhub.services.customer.cart.CartServiceImpl;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CartServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CartItemsRepository cartItemsRepository;
    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testaddProductToCart(){
        AddProductInCartDto addProductInCartDto = new AddProductInCartDto();
        addProductInCartDto.setUserId(1L);
        addProductInCartDto.setProductId(1L);
        List<CartItems> cartItems = new ArrayList<>();
        CartItems ct = new CartItems();
        ct.setQuantity(12L);
        Order activeOrder = new Order();
        activeOrder.setId(1L);
        activeOrder.setTotalAmount(123L);
        activeOrder.setAmount(123L);
        activeOrder.setCartItems(cartItems);
        activeOrder.setOrderStatus(OrderStatus.Pending);
        when(orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending))
                .thenReturn(activeOrder);

        when(cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()))
                .thenReturn(Optional.empty());

        Product product = new Product();
        product.setPrice(100L);
        when(productRepository.findById(addProductInCartDto.getProductId())).thenReturn(Optional.of(product));
        User user = new User();
        when(userRepository.findById(addProductInCartDto.getUserId())).thenReturn(Optional.of(user));
        ResponseEntity<?> result = cartService.addProductToCart(addProductInCartDto);
    }

    @Test
    public void testgetCartByUserId(){
        Long userId = 1L;
        Order activeOrder = new Order();
        activeOrder.setId(1L);
        activeOrder.setAmount(100L);
        activeOrder.setTotalAmount(120L);
        activeOrder.setOrderStatus(OrderStatus.Pending);
        Product product = new Product();
        product.setPrice(100L);
        List<CartItems> cartItems = new ArrayList<>();
        CartItems cartItem1 = new CartItems();
        cartItem1.setId(1L);
        cartItem1.setQuantity(2L);
        cartItems.add(cartItem1);
        cartItem1.setProduct(product);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        cartItem1.setUser(user);
        activeOrder.setCartItems(cartItems);
        when(orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending)).thenReturn(activeOrder);
        OrderDto result = cartService.getCartByUserId(userId);
    }
    @Test
    public void testapplyCoupon(){
        Long userId = 1L;
        String couponCode = "CODE123";
        Order activeOrder = new Order();
        activeOrder.setId(1L);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        activeOrder.setTotalAmount(1000L);
        activeOrder.setUser(user);
        when(orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending)).thenReturn(activeOrder);
        Coupon coupon = new Coupon();
        coupon.setId(1L);
        coupon.setCode(couponCode);
        coupon.setDiscount(10L);
        when(couponRepository.findByCode(couponCode)).thenReturn(Optional.of(coupon));
        OrderDto result = cartService.applyCoupon(userId, couponCode);
        assertNotNull(result);
    }
    @Test
    public void testincreaseProductQuantity(){
        AddProductInCartDto addProductInCartDto = new AddProductInCartDto();
        addProductInCartDto.setUserId(1L);
        addProductInCartDto.setProductId(1L);
        Order activeOrder = new Order();
        activeOrder.setId(1L);
        activeOrder.setAmount(100L);
        activeOrder.setTotalAmount(120L);
        activeOrder.setOrderStatus(OrderStatus.Pending);
        Coupon coupon = new Coupon();
        coupon.setName("test name");
        coupon.setDiscount(1L);
        activeOrder.setCoupon(coupon);
        when(orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending))
                .thenReturn(activeOrder);
        Product product = new Product();
        product.setId(1L);
        product.setPrice(20L);
        when(productRepository.findById(addProductInCartDto.getProductId())).thenReturn(Optional.of(product));
        CartItems cartItem1 = new CartItems();
        cartItem1.setId(1L);
        cartItem1.setQuantity(2L);
        cartItem1.setProduct(product);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        cartItem1.setUser(user);
        activeOrder.setUser(user);
        when(cartItemsRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()))
                .thenReturn(Optional.of(cartItem1));

        OrderDto result = cartService.increaseProductQuantity(addProductInCartDto);

    }

    @Test
    public void testdecreaseProductQuantity(){
        AddProductInCartDto addProductInCartDto = new AddProductInCartDto();
        addProductInCartDto.setUserId(1L);
        addProductInCartDto.setProductId(1L);

        Order activeOrder = new Order();
        activeOrder.setId(1L);
        activeOrder.setAmount(200L);
        activeOrder.setTotalAmount(220L);
        activeOrder.setOrderStatus(OrderStatus.Pending);
        Coupon coupon = new Coupon();
        coupon.setName("test name");
        coupon.setDiscount(1L);
        activeOrder.setCoupon(coupon);
        Product product = new Product();
        product.setId(1L);
        product.setPrice(20L);
        CartItems cartItem1 = new CartItems();
        cartItem1.setId(1L);
        cartItem1.setQuantity(2L);
        cartItem1.setProduct(product);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        cartItem1.setUser(user);
        activeOrder.setUser(user);
        when(orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending))
                .thenReturn(activeOrder);
        when(productRepository.findById(addProductInCartDto.getProductId())).thenReturn(Optional.of(product));
        when(cartItemsRepository.findByProductIdAndOrderIdAndUserId(addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()))
                .thenReturn(Optional.of(cartItem1));
        OrderDto result = cartService.decreaseProductQuantity(addProductInCartDto);

    }

    @Test
    public void testplaceOrder(){
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        placeOrderDto.setUserId(1L);
        placeOrderDto.setOrderDescription("Test order");
        placeOrderDto.setAddress("123 Test St");
        Order activeOrder = new Order();
        activeOrder.setId(1L);
        activeOrder.setAmount(200L);
        activeOrder.setTotalAmount(220L);
        activeOrder.setOrderStatus(OrderStatus.Pending);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        activeOrder.setUser(user);
        when(orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending))
                .thenReturn(activeOrder);
        when(userRepository.findById(placeOrderDto.getUserId())).thenReturn(Optional.of(user));

        OrderDto result = cartService.placeOrder(placeOrderDto);
    }

    @Test
    public void testGetMyPlacedOrders_Success() {
        Long userId = 1L;
        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderStatus(OrderStatus.Placed);
        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderStatus(OrderStatus.Shipped);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        order1.setUser(user);
        order2.setUser(user);
        when(orderRepository.findByUserIdAndOrderStatusIn(userId, Arrays.asList(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered)))
                .thenReturn(Arrays.asList(order1, order2));
        List<OrderDto> result = cartService.getMyPlacedOrders(userId);
    }

    @Test
    public void testSearchOrderByTrackingId_Success() {
        UUID trackingId = UUID.randomUUID();
        Order order = new Order();
        order.setId(1L);
        order.setTrackingId(trackingId);
        User user = new User();
        user.setName("test name");
        user.setId(12L);
        order.setUser(user);
        when(orderRepository.findByTrackingId(trackingId)).thenReturn(Optional.of(order));
        OrderDto result = cartService.searchOrderByTrackingId(trackingId);

    }
}
