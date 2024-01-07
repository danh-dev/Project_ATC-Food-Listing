package vn.hdweb.team9.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.dto.CouponSearch;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
@RequiredArgsConstructor
public class CouponRepository implements ICouponRepository {

    private final EntityManager em;


    @Override
    public void save(Coupon coupon) {
        if (coupon.getId() == null) {
            em.persist(coupon);
        } else {
            em.merge(coupon);
        }
    }

    @Override
    public void saveAll(Iterable<Coupon> coupons) {
        for (Coupon coupon : coupons) { em.persist(coupon); }
    }

    @Override
    public Optional<Coupon> findOne(Long id) {
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
    public List<Coupon> findAllByCriteria(CouponSearch couponSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Coupon> cq = cb.createQuery(Coupon.class);
        Root<Coupon> o = cq.from(Coupon.class);
        return findAll();
    }

    @Override
    public Optional<Coupon> update(Coupon coupon) {
       return Optional.ofNullable(em.merge(coupon));
    }

    @Override
    public void deleteOne(Long id) {
        Optional<Coupon> getCoupon = findOne(id);

        if (!getCoupon.isEmpty()) {
            em.remove(getCoupon.get());
        } else {
            throw new EntityNotFoundException("Coupon with id ["+ id + "] does not exist");
        }
    }
}
