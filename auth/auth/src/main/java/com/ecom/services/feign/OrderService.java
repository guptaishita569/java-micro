package com.ecom.services.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cart-order-service", url = "http://localhost:8083")
public interface OrderService {

    @GetMapping("/order/customer/{userId}")
    ResponseEntity<?> createNewOrder(@PathVariable("userId") Long userId);

}
