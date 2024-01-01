package vn.hdweb.team9.controller.admin;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.service.CouponService;

import java.util.List;

@Controller
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) { this.couponService = couponService; }

    @GetMapping("admin/coupon/new")
    public String createCoupon() { return "admin/coupon/createCoupon"; }


    @PostMapping("admin/coupon/new")
    public String createCoupon(@ModelAttribute @NotNull CouponForm couponForm) {
        Coupon coupon = new Coupon();
        coupon.setCouponQuantity(couponForm.getCouponQuantity());
        coupon.setCouponName(couponForm.getCouponName());
        couponService.create(coupon);
        return "redirect:/admin/coupon/list";
    }

    @GetMapping("admin/coupon/list")
    public String couponList(Model model) {
        List<Coupon> coupons = couponService.findAll();
        model.addAttribute("coupons", coupons);
        return "admin/coupon/couponList";
    }



}
