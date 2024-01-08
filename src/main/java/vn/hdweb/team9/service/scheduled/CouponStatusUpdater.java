package vn.hdweb.team9.service.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.CouponRepository;

@Component
public class CouponStatusUpdater {

    private final CouponRepository couponRepository;

    public CouponStatusUpdater(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Scheduled(cron = "0 0 0 * * ?")        // at midnight every day
    public void updateCouponStatuses() {
        Iterable<Coupon> coupons = couponRepository.findAll();
        coupons.forEach(Coupon::updateStatus);
        couponRepository.saveAll(coupons);
    }
}
