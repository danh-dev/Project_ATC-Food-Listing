package vn.hdweb.team9.repository.interfaces;

import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.dto.CouponSearch;
import vn.hdweb.team9.domain.entity.Coupon;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ICouponRepository {
    // CREATE
    // Save new coupon
    void save(Coupon coupon);

    // Save all
    void saveAll(Iterable<Coupon> coupons);

    // READ
    // Find by ID
    Optional<Coupon> findOne(Long id);

    // Find by Code
    Optional<Coupon> findByCouponCode(String couponCode);

    // Find by Criteria
    List<Coupon> findAllByCriteria(CouponSearch couponSearch);

    // Find all
    List<Coupon> findAll();

    // UPDATE
    // Update existing coupon
    Optional<Coupon> update(Coupon coupon);

    // DELETE
    // Delete by id
    void deleteOne(Long id);

}
