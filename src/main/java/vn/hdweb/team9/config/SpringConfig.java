package vn.hdweb.team9.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.repository.CouponRepository;
import vn.hdweb.team9.repository.interfaces.ICouponRepository;
import vn.hdweb.team9.service.CouponService;

@Configuration
public class SpringConfig {

    // private final DataSource dataSource;
    private final EntityManager em;

    public SpringConfig(EntityManager em) { this.em = em; }

    @Bean
    public CouponService postService() {
        return new CouponService(couponRepository());
    }

    @Bean
    public ICouponRepository couponRepository() { return new CouponRepository(em); }

}
