package com.ecom.services.feign;

import com.ecom.dto.CouponDto;
import com.ecom.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "category-product-service", url = "http://localhost:8082")
public interface ProductService {

    @GetMapping("/product/admin/product/{id}")
    ResponseEntity<ProductDto> getProductById(@PathVariable("id") Long id);

    @GetMapping("/product/admin/coupons/{code}")
    ResponseEntity<CouponDto> findCouponByCode(@PathVariable("code") String code);

    @GetMapping("/product/admin/coupon/{couponId}")
    ResponseEntity<CouponDto> findCouponById(@PathVariable("couponId") Long couponId);

}
