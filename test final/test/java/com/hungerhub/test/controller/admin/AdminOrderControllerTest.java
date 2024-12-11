package com.hungerhub.test.controller.admin;

import com.hungerhub.controller.admin.AdminOrderController;
import com.hungerhub.dto.AnalyticsResponse;
import com.hungerhub.dto.OrderDto;
import com.hungerhub.services.admin.adminOrder.AdminOrderService;
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
import static org.mockito.Mockito.when;

public class AdminOrderControllerTest {

    @Mock
    private AdminOrderService adminOrderService;
    @InjectMocks
    private AdminOrderController adminOrderController;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPlacedOrders() {
        List<OrderDto> orders = new ArrayList<>();
        when(adminOrderService.getAllPlacedOrders()).thenReturn(orders);
        ResponseEntity<List<OrderDto>> responseEntity = adminOrderController.getAllPlacedOrders();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orders, responseEntity.getBody());
    }

    @Test
    public void testChangeOrderStatus() {
        Long orderId = 123L;
        String status = "shipped";
        OrderDto orderDto = new OrderDto();
        when(adminOrderService.changeOrderStatus(orderId, status)).thenReturn(orderDto);
        ResponseEntity<?> responseEntity = adminOrderController.changeOrderStatus(orderId, status);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(orderDto, responseEntity.getBody());
    }

    @Test
    public void testChangeOrderStatus_Error() {
        Long orderId = 123L;
        String status = "invalid_status";
        when(adminOrderService.changeOrderStatus(orderId, status)).thenReturn(null);
        ResponseEntity<?> responseEntity = adminOrderController.changeOrderStatus(orderId, status);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Something went wrong!", responseEntity.getBody());
    }

//    @Test
//    public void testGetAnalytics() {
//        AnalyticsResponse analyticsResponse = new AnalyticsResponse();
//        when(adminOrderService.calculateAnalytics()).thenReturn(analyticsResponse);
//        ResponseEntity<AnalyticsResponse> responseEntity = adminOrderController.getAnalytics();
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//        assertEquals(analyticsResponse, responseEntity.getBody());
//    }

}
