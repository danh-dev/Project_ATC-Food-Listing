package vn.hdweb.team9.service;

import jakarta.transaction.Transactional;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;

import java.util.List;
import java.util.Optional;

@Transactional
public class CouponService {
    private final ICouponRepository couponRepository;

    public CouponService(ICouponRepository couponRepository) { this.couponRepository = couponRepository; }

    public Long create(Coupon coupon) {
        couponRepository.create(coupon);
        return coupon.getId();
    }

    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }

    public Optional<Coupon> findById(Long id) {
        return couponRepository.findById(id);
    }

    public Optional<Coupon> findByCouponCode(String couponCode) {
        return couponRepository.findByCouponCode(couponCode);
    }


    public Optional<Coupon> update(Coupon coupon) {
        return couponRepository.update(coupon);
    }

    public void delete(Coupon coupon) {
        couponRepository.delete(coupon);
    }

}
