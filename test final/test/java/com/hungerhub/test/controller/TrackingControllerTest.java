package com.hungerhub.test.controller;

import com.hungerhub.controller.TrackingController;
import com.hungerhub.dto.OrderDto;
import com.hungerhub.services.customer.cart.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TrackingControllerTest {
    @Mock
    private CartService cartService;

    @InjectMocks
    private TrackingController trackingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchOrderByTrackingIdOrderFound() {
        UUID trackingId = UUID.randomUUID();
        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(123L);
        orderDto.setId(12L);
        orderDto.setTrackingId(trackingId);
        when(cartService.searchOrderByTrackingId(any())).thenReturn(orderDto);
        ResponseEntity<OrderDto> responseEntity = trackingController.searchOrderByTrackingId(trackingId);
    }
}
