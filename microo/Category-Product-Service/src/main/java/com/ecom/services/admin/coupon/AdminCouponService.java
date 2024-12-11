package com.ecom.services.admin.coupon;

import com.ecom.dto.CouponDto;
import com.ecom.entity.Coupon;

import java.util.List;

public interface AdminCouponService {

    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();

    CouponDto findCouponByCode(String code);

    CouponDto findCouponById(Long couponId);
}
