package com.hungerhub.services.admin.adminOrder;

import java.util.List;

import com.hungerhub.dto.AnalyticsResponse;
import com.hungerhub.dto.OrderDto;

public interface AdminOrderService {

    List<OrderDto> getAllPlacedOrders();

    OrderDto changeOrderStatus(Long orderId, String status);

    AnalyticsResponse calculateAnalytics();
}
