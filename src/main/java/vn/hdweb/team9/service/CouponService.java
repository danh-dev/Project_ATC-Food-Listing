package vn.hdweb.team9.service;

import jakarta.transaction.Transactional;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;

@Transactional
public class CouponService {
    private final ICouponRepository couponRepository;

    public CouponService(ICouponRepository couponRepository) { this.couponRepository = couponRepository; }

    public Long save(Coupon coupon) {
        couponRepository.save(coupon);
        return coupon.getId();
    }

}
