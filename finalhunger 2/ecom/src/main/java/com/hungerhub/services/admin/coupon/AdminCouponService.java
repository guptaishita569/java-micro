package com.hungerhub.services.admin.coupon;

import java.util.List;

import com.hungerhub.entity.Coupon;

public interface AdminCouponService {

    Coupon createCoupon(Coupon coupon);

    List<Coupon> getAllCoupons();
}
