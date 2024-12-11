//package com.hungerHub.ecom;
//
//public class AdminCouponControllerTest {
//
//}
package com.hungerHub.ecom;

import com.ecom.controller.admin.AdminCouponController;
import com.ecom.entity.Coupon;
import com.ecom.exceptions.ValidationException;
import com.ecom.services.admin.coupon.AdminCouponService;
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

public class AdminCouponControllerTest {

    @InjectMocks
    private AdminCouponController adminCouponController;

    @Mock
    private AdminCouponService adminCouponService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCoupon() {
        Coupon coupon = new Coupon();
        Coupon createdCoupon = new Coupon();
        when(adminCouponService.createCoupon(coupon)).thenReturn(createdCoupon);
        ResponseEntity<?> responseEntity = adminCouponController.createCoupon(coupon);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdCoupon, responseEntity.getBody());

    }
    @Test
    public void testCreateCoupon_ValidationException() {
        // Mock data
        Coupon coupon = new Coupon();
        ValidationException validationException = new ValidationException("Invalid coupon data");
        when(adminCouponService.createCoupon(coupon)).thenThrow(validationException);
        ResponseEntity<?> responseEntity = adminCouponController.createCoupon(coupon);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals(validationException.getMessage(), responseEntity.getBody());
    }

    @Test
    public void testGetAllCoupons() {
        List<Coupon> coupons = new ArrayList<>();
        when(adminCouponService.getAllCoupons()).thenReturn(coupons);
        ResponseEntity<List<Coupon>> responseEntity = adminCouponController.getAllCoupons();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(coupons, responseEntity.getBody());
    }


}
