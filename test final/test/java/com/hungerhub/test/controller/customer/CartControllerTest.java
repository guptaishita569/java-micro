package com.hungerhub.test.controller.customer;

import com.hungerhub.controller.customer.CartController;
import com.hungerhub.dto.AddProductInCartDto;
import com.hungerhub.dto.OrderDto;
import com.hungerhub.dto.PlaceOrderDto;
import com.hungerhub.exceptions.ValidationException;
import com.hungerhub.services.customer.cart.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class CartControllerTest {

    @InjectMocks
    private CartController cartController;

    @Mock
    private CartService cartService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddProductToCart() {
        AddProductInCartDto addProductInCartDto = new AddProductInCartDto();
        ResponseEntity<?> responseEntity = ResponseEntity.ok().build();
        ResponseEntity<?> result = cartController.addProductToCart(addProductInCartDto);
    }

    @Test
    void testGetCartByUserId() {
        Long userId = 1L;
        OrderDto orderDto = new OrderDto();
        when(cartService.getCartByUserId(userId)).thenReturn(orderDto);
        ResponseEntity<?> result = cartController.getCartByUserId(userId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderDto, result.getBody());
        verify(cartService, times(1)).getCartByUserId(userId);
    }

    @Test
    void testApplyCoupon() throws ValidationException {
        Long userId = 1L;
        String code = "COUPON";
        OrderDto orderDto = new OrderDto();
        when(cartService.applyCoupon(userId, code)).thenReturn(orderDto);
        ResponseEntity<?> result = cartController.applyCoupon(userId, code);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderDto, result.getBody());
        verify(cartService, times(1)).applyCoupon(userId, code);
    }

    @Test
    void testIncreaseProductQuantity() {
        AddProductInCartDto addProductInCartDto = new AddProductInCartDto();
        OrderDto orderDto = new OrderDto();
        when(cartService.increaseProductQuantity(addProductInCartDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> result = cartController.increaseProductQuantity(addProductInCartDto);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(orderDto, result.getBody());
        verify(cartService, times(1)).increaseProductQuantity(addProductInCartDto);
    }

    @Test
    void testDecreaseProductQuantity() {
        AddProductInCartDto addProductInCartDto = new AddProductInCartDto();
        OrderDto orderDto = new OrderDto();
        when(cartService.decreaseProductQuantity(addProductInCartDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> result = cartController.decreaseProductQuantity(addProductInCartDto);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(orderDto, result.getBody());
        verify(cartService, times(1)).decreaseProductQuantity(addProductInCartDto);
    }

    @Test
    void testPlaceOrder() {
        PlaceOrderDto placeOrderDto = new PlaceOrderDto();
        OrderDto orderDto = new OrderDto();
        when(cartService.placeOrder(placeOrderDto)).thenReturn(orderDto);
        ResponseEntity<OrderDto> result = cartController.placeOrder(placeOrderDto);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(orderDto, result.getBody());
        verify(cartService, times(1)).placeOrder(placeOrderDto);
    }

    @Test
    void testGetMyPlacedOrders() {
        Long userId = 1L;
        List<OrderDto> orderDtos = new ArrayList<>();
        when(cartService.getMyPlacedOrders(userId)).thenReturn(orderDtos);
        ResponseEntity<List<OrderDto>> result = cartController.getMyPlacedOrders(userId);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(orderDtos, result.getBody());
        verify(cartService, times(1)).getMyPlacedOrders(userId);
    }

}
