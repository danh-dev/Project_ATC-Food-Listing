package vn.hdweb.team9.controller.admin;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.service.CouponService;

@Controller
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) { this.couponService = couponService; }

    @GetMapping("admin/coupons/create")
    public String createCoupon() { return "admin/coupons/createCoupon"; }


    @PostMapping("admin/coupons/create")
    public String createCoupon(@ModelAttribute @NotNull CouponForm couponForm) {
        Coupon coupon = new Coupon();
        coupon.setCouponQuantity(couponForm.getCouponQuantity());
        coupon.setCouponName(couponForm.getCouponName());
        couponService.create(coupon);
        return "redirect:/admin/coupons";
    }

    @GetMapping("admin/coupons")
    public String couponList() { return "admin/coupons/couponList"; }
}
