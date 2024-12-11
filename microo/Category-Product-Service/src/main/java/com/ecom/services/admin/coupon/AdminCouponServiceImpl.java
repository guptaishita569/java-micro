package com.ecom.services.admin.coupon;

import com.ecom.dto.CouponDto;
import com.ecom.entity.Coupon;
import com.ecom.exceptions.ValidationException;
import com.ecom.repository.CouponRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminCouponServiceImpl implements AdminCouponService {
@Autowired
    private CouponRepository couponRepository;


    public Coupon createCoupon(Coupon coupon) {
        if (couponRepository.existsByCode(coupon.getCode())) {
            throw new ValidationException("Coupon code already exists.");
        }
        return couponRepository.save(coupon);
    }

    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public CouponDto findCouponByCode(String code) {
        Optional<Coupon> optionalCoupon = couponRepository.findByCode(code);
        return optionalCoupon.map(Coupon::getCouponDto).orElse(null);
    }

    @Override
    public CouponDto findCouponById(Long couponId) {
        Optional<Coupon> optionalCoupon = couponRepository.findById(couponId);
        return optionalCoupon.map(Coupon::getCouponDto).orElse(null);
    }
}
