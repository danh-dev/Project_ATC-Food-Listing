package vn.hdweb.team9.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.CouponRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@SpringBootTest
@Transactional
class CouponServiceTest {

    @Autowired CouponRepository couponRepository;
    @Autowired CouponService couponService;

    @Test
    public void create() {
        // given
        Coupon coupon = new Coupon();
        coupon.setCouponName("Discount 20%");
        coupon.setCouponQuantity(100);

        // when
        Long couponId = couponService.create(coupon);

        // then
        Optional<Coupon> result = couponRepository.findOne(couponId);
        assertThat(couponId).isEqualTo(result.get().getId());
    }


    @Test
    void findAll() {
        // given
        Coupon coupon1 = new Coupon();
        coupon1.setCouponName("Coupon 1");
        coupon1.setCouponQuantity(10);

        Coupon coupon2 = new Coupon();
        coupon2.setCouponName("Coupon 2");
        coupon2.setCouponQuantity(10);

        couponService.create(coupon1);
        couponService.create(coupon2);

        // when
        List<Coupon> results = couponRepository.findAll();

        // then
        List<Coupon> coupons = new ArrayList<>();
        coupons.add(coupon1);
        coupons.add(coupon2);
        assertThat(results).isEqualTo(coupons);
    }

    @Test
    void findById() {
        // given
        Coupon coupon1 = new Coupon();
        coupon1.setCouponName("Coupon 1");
        coupon1.setCouponQuantity(10);
        couponService.create(coupon1);

        Coupon coupon2 = new Coupon();
        coupon2.setCouponName("Coupon 2");
        coupon2.setCouponQuantity(10);
        couponService.create(coupon2);

        // when
        Optional<Coupon> result = couponRepository.findOne(coupon1.getId());

        //then
        assertThat(result.get()).isEqualTo(coupon1);
    }

    @Test
    void findByCouponCode() {
        // given
        Coupon coupon1 = new Coupon();
        coupon1.setCouponName("Coupon 1");
        coupon1.setCouponCode("ABC");
        coupon1.setCouponQuantity(10);
        couponService.create(coupon1);

        // when
        Optional<Coupon> result = couponRepository.findByCouponCode("ABC");

        //then
        assertThat(result.get()).isEqualTo(coupon1);
    }

    @Test
    void update() {
        // given
        Coupon couponOld = new Coupon();
        couponOld.setCouponCode("ACB");
        couponService.create(couponOld);

        // when
        Coupon couponNew = new Coupon();
        couponNew.setCouponCode("ABC");

    }

    @Test
    void delete() {
        // given
        Coupon coupon1 = new Coupon();
        coupon1.setCouponName("Coupon 1");
        couponService.create(coupon1);
        couponService.create(coupon1);

        Coupon coupon2 = new Coupon();
        coupon2.setCouponName("Coupon 2");
        couponService.create(coupon2);
        couponService.create(coupon2);

        // when
        couponRepository.deleteOne(coupon1.getId());
        List<Coupon> results = couponRepository.findAll();

        // then
        assertThat(results.toArray().length).isEqualTo(1);

    }

    @Test
    void decreaseCouponQuantity() {
        // given
        Coupon coupon1 = new Coupon();
        coupon1.setCouponName("Coupon 1");
        coupon1.setCouponQuantity(100);
        couponService.create(coupon1);

        // when
        coupon1.decreaseCouponQuantity();
        Coupon getCoupon = couponService.findOne(coupon1.getId()).get();

        // then
        assertThat(getCoupon.getCouponQuantity()).isEqualTo(99);

    }

}