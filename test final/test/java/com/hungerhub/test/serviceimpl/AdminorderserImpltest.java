package com.hungerhub.test.serviceimpl;
import static org.mockito.Mockito.*;

import java.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.hungerhub.dto.OrderDto;
import com.hungerhub.entity.CartItems;
import com.hungerhub.entity.Coupon;
import com.hungerhub.entity.Order;
import com.hungerhub.entity.User;
import com.hungerhub.enums.OrderStatus;
import com.hungerhub.repository.OrderRepository;
import com.hungerhub.services.admin.adminOrder.AdminOrderServiceImpl;
public class AdminorderserImpltest {
    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private AdminOrderServiceImpl adminOrderService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void estchangeOrderStatus() {
        Long orderId = 123L;
        String status="Shipped";
        User user = new User();
        user.setName("ram");
        List<CartItems> cartItems = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderStatus(OrderStatus.Placed);
        order1.setOrderDescription("sample");
        order1.setAmount(12L);
        order1.setUser(user);
        order1.setAddress("test address");
        order1.setDiscount(1L);
        order1.setId(123L);
        order1.setDate(new Date());
        order1.setTotalAmount(1234L);
        order1.setTrackingId(new UUID(123L,2L));
        order1.setCoupon(new Coupon());
        order1.setCartItems(cartItems);
        order1.setPayment("123");
        Mockito.when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order1));
        when(orderRepository.save(order1)).thenReturn(order1);
        adminOrderService.changeOrderStatus(orderId,status);
    }

    @Test
    public void testgetAllPlacedOrders(){
        User user = new User();
        user.setName("ram");
        List<CartItems> cartItems = new ArrayList<>();
        Order order1 = new Order();
        order1.setOrderStatus(OrderStatus.Placed);
        order1.setOrderDescription("sample");
        order1.setAmount(12L);
        order1.setUser(user);
        order1.setAddress("test address");
        order1.setDiscount(1L);
        order1.setId(123L);
        order1.setDate(new Date());
        order1.setTotalAmount(1234L);
        order1.setTrackingId(new UUID(123L,2L));
        order1.setCoupon(new Coupon());
        order1.setCartItems(cartItems);
        order1.setPayment("123");
        Order order2 = new Order(/* fill in order data */);
        List<Order> orders = Arrays.asList(order1);
        Mockito.when(orderRepository.findAllByOrderStatusIn(Arrays.asList(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered))).thenReturn(orders);
        List<OrderDto> result = adminOrderService.getAllPlacedOrders();
    }
    
    @Test
    public void testcalculateAnalytics() {
    	adminOrderService.calculateAnalytics();
    }


}
