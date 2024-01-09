package vn.hdweb.team9.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.hdweb.team9.domain.dto.CouponForm;
import vn.hdweb.team9.domain.dto.CouponSearch;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.CouponStatus;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponService {
    private final ICouponRepository couponRepository;

    /**
     * Create Coupon
     */
    @Transactional
    public Long create(Coupon coupon) {
        validateDuplicateCouponCode(coupon);
        validateStartEndDate(coupon);
        couponRepository.save(coupon);
        return coupon.getId();
    }


    /**
     * Find All Coupons
     */
    public List<Coupon> findAll() {
        return couponRepository.findAll();
    }


    /**
     * Find One
     */
    public Optional<Coupon> findOne(Long id) {
        return couponRepository.findOne(id);
    }


    /**
     * Find By Coupon Code
     */
    public Optional<Coupon> findByCouponCode(String couponCode) {
        return couponRepository.findByCouponCode(couponCode);
    }


    /**
     * Find Coupon by Criteria
     */
    public List<Coupon> findCoupons(CouponSearch couponSearch) {
        return couponRepository.findAllByCriteria(couponSearch);
    }

    /**
     * Update Coupon
     */
    @Transactional
    public void update(CouponForm couponForm) {
        Optional<Coupon> getCoupon = couponRepository.findOne(couponForm.getId());

        if (getCoupon.isEmpty()) {
            throw new IllegalStateException("Coupon code does not exist!");
        }

        Coupon coupon = getCoupon(couponForm, getCoupon);
        coupon.updateStatus();

        validateStartEndDate(coupon);
        couponRepository.save(coupon);
    }

    private static Coupon getCoupon(CouponForm couponForm, Optional<Coupon> getCoupon) {
        Coupon coupon = getCoupon.get();
        coupon.setCouponName(couponForm.getCouponName());
        coupon.setCouponCode(couponForm.getCouponCode());
        coupon.setCouponDescription(couponForm.getCouponDescription());
        coupon.setTypeCoupon(couponForm.getTypeCoupon());
        coupon.setCouponValue(couponForm.getCouponValue());
        coupon.setCouponQuantity(couponForm.getCouponQuantity());
        coupon.setStartDate(couponForm.getStartDate());
        coupon.setEndDate(couponForm.getEndDate());
        return coupon;
    }

    public void validateDuplicateCouponCode(Coupon coupon) {
        Optional<Coupon> getCoupon = couponRepository.findByCouponCode(coupon.getCouponCode());
        if (!getCoupon.isEmpty()) {
            throw new IllegalStateException("Coupon code already exists!");
        }
    }


    public void validateStartEndDate(Coupon coupon) {
        LocalDate startDate = coupon.getStartDate();
        LocalDate endDate = coupon.getEndDate();
        if (startDate.isAfter(endDate)) {
            throw new DateTimeException("Start date can not after end date!");
        }
    }


    /**
     * Delete Coupon
     */
    @Transactional
    public void deleteOne(Long id) {
        couponRepository.deleteOne(id);
    }

}
