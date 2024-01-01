package vn.hdweb.team9.repository;


import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


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
        List<Coupon> results = em.createQuery("select c from Coupon c where c.couponCode = :coupon_code", Coupon.class)
                .setParameter("coupon_code", couponCode)
                .getResultList();
        return results.stream().findAny();
    }

    @Override
    public List<Coupon> findAll() {
        return em.createQuery("select c from Coupon c", Coupon.class)
                .getResultList();
    }

    @Override
    public Optional<Coupon> update(Coupon coupon) {
       return Optional.ofNullable(em.merge(coupon));
    }

    @Override
    public void delete(Coupon coupon) {
        em.remove(coupon);
    }


    @Override
    public void clearAll() { em.clear(); }
}
