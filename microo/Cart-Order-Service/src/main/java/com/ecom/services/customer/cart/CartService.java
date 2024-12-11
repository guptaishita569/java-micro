package com.ecom.services.customer.cart;

import com.ecom.dto.AddProductInCartDto;
import com.ecom.dto.OrderDto;
import com.ecom.dto.OrderedProductsResponseDto;
import com.ecom.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId, String code);

    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);

    OrderDto searchOrderByTrackingId(UUID trackingId);

    OrderDto createNewOrder(Long userId);

    OrderDto getOrderById(Long orderId);

    OrderedProductsResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

}
