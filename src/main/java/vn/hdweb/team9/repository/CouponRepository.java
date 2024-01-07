package vn.hdweb.team9.repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.dto.CouponSearch;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.CouponStatus;
import vn.hdweb.team9.domain.entity.TypeCoupon;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;

import java.util.ArrayList;
import java.util.Arrays;
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
        Root<Coupon> root = cq.from(Coupon.class);
        List<Predicate> predicates = new ArrayList<>();

        if (couponSearch.getCouponName() != null) {
            Predicate name = cb.like(
                    root.get("couponName"), "%" + couponSearch.getCouponName() + "%"
            );
            predicates.add(name);
        }

        if (couponSearch.getCouponCode() != null) {
            Predicate code = cb.like(
                    root.get("couponCode"), "%" + couponSearch.getCouponCode() + "%"
            );
            predicates.add(code);
        }

        if (couponSearch.getCouponStatus() != null &&
                Arrays.stream(CouponStatus.values()).
                anyMatch(couponSearch.getCouponStatus()::equals)) {
            Predicate status = cb.equal(
                    root.get("couponStatus"), couponSearch.getCouponStatus()
            );
            predicates.add(status);
        }

        if (couponSearch.getTypeCoupon() != null &&
                Arrays.stream(TypeCoupon.values()).
                anyMatch(couponSearch.getTypeCoupon()::equals)) {
            Predicate type = cb.equal(
                    root.get("typeCoupon"), couponSearch.getTypeCoupon()
            );
            predicates.add(type);
        }

        if (couponSearch.getCouponValue() > 0) {
            Predicate value = cb.equal(
                    root.get("couponValue"), couponSearch.getCouponValue()
            );
            predicates.add(value);
        }

        if (couponSearch.getCouponQuantity() > 0) {
            Predicate quantity = cb.equal(
                    root.get("couponQuantity"), couponSearch.getCouponQuantity()
            );
            predicates.add(quantity);
        }



        if (couponSearch.getStartDate() != null) {
            Predicate startDate = cb.greaterThanOrEqualTo(
                    root.get("startDate"), couponSearch.getStartDate()
            );
            predicates.add(startDate);
        }

        if (couponSearch.getEndDate() != null) {
            Predicate endDate = cb.lessThanOrEqualTo(
                    root.get("endDate"), couponSearch.getEndDate()
            );
            predicates.add(endDate);
        }

        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        TypedQuery<Coupon> query = em.createQuery(cq).setMaxResults(1000);
        return query.getResultList();
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
