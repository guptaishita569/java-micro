package com.hungerhub.test.serviceimpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hungerhub.entity.Coupon;
import com.hungerhub.repository.CouponRepository;
import com.hungerhub.services.admin.coupon.AdminCouponServiceImpl;

public class AdminCouponServiceImplTest {

    @Mock
    private CouponRepository couponRepository;
    @InjectMocks
    private AdminCouponServiceImpl adminCouponService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testcreateCoupon(){
        Coupon coupon = new Coupon();
        coupon.setCode("123");
        coupon.setName("test");
        adminCouponService.createCoupon(coupon);
    }
    @Test
    public  void testgetAllCoupons(){
        adminCouponService.getAllCoupons();
    }

}
