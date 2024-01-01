package vn.hdweb.team9.repository.interfaces;

import org.springframework.stereotype.Repository;
import vn.hdweb.team9.domain.entity.Coupon;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ICouponRepository {
    // CREATE
    // 1. Save new coupon
    Coupon create(Coupon coupon);

    // READ
    // 2. Find by ID
    Optional<Coupon> findById(Long id);

    // 3. Find by Code
    Optional<Coupon> findByCouponCode(String couponCode);

    // 4. Find all
    List<Coupon> findAll();

    // UPDATE
    // 5. Update existing coupon
    Optional<Coupon> update(Coupon coupon);

    // DELETE
    // 6. Delete by entity
    void delete(Coupon coupon);


    void clearAll();

}
