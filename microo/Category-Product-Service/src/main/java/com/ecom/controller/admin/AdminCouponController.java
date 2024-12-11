package com.ecom.controller.admin;

import com.ecom.entity.Coupon;
import com.ecom.exceptions.ValidationException;
import com.ecom.services.admin.coupon.AdminCouponService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/admin/coupons")
//@RequiredArgsConstructor
public class AdminCouponController {
@Autowired
    private AdminCouponService adminCouponService;

    @PostMapping

    public ResponseEntity<?> createCoupon(@RequestBody Coupon coupon) {
        try {
            Coupon createdCoupon = adminCouponService.createCoupon(coupon);
            return ResponseEntity.ok(createdCoupon);
        } catch (ValidationException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping
//    ("/coupons")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        return ResponseEntity.ok(adminCouponService.getAllCoupons());
    }

    @GetMapping("/{code}")
    public ResponseEntity<?> findCouponByCode(@PathVariable String code) {
        return ResponseEntity.ok(adminCouponService.findCouponByCode(code));
    }

}
