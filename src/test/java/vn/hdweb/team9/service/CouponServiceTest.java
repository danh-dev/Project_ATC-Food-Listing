package vn.hdweb.team9.service;

import jakarta.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.CouponRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class CouponServiceTest {
    @Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponService couponService;

    @Test
    public void save() {
        // given
        Coupon coupon = new Coupon();
        coupon.setCouponName("Discount 20%");
        coupon.setCouponQuantity(100);

        // when
        Long couponId = couponService.create(coupon);

        // then
        Optional<Coupon> result = couponRepository.findById(couponId);

        assertThat(couponId).isEqualTo(result.get().getId());
        System.out.println(coupon);

    }



}