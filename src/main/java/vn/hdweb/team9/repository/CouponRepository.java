package vn.hdweb.team9.repository;


import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;


public class CouponRepository implements ICouponRepository {

    private final EntityManager em;

    @Autowired
    public CouponRepository(EntityManager em) { this.em = em; }


    @Override
    public Coupon create(Coupon coupon) {
        em.persist(coupon);
        return coupon;
    }

    @Override
    public Optional<Coupon> findById(Long id) {
        Coupon coupon =em.find(Coupon.class, id);
        return Optional.ofNullable(coupon);
    }

    @Override
    public Optional<Coupon> findByCouponCode(String couponCode) {
        List<Coupon> coupons = em.createQuery("select c from Coupon c where c.coupon_code = :coupon_code", Coupon.class)
                .setParameter("coupon_code", couponCode)
                .getResultList();
        return coupons.stream().findAny();
    }

    @Override
    public List<Coupon> findAll() {
        return em.createQuery("select c from Coupon c", Coupon.class)
                .getResultList();
    }

    @Override
    public Optional<Coupon> saveAndFlush(Coupon coupon) {
        return Optional.empty();
    }

    @Override
    public void delete(Coupon coupon) {

    }
}
