package vn.hdweb.team9.controller.admin;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.service.CouponService;

import javax.swing.text.html.Option;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/coupon")
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) { this.couponService = couponService; }

    @GetMapping("/new")
    public String createCoupon(Model model) {
        Coupon coupon = new Coupon();
        model.addAttribute("coupon", coupon);
        return "admin/coupon/createCoupon";
    }


    @PostMapping("/new")
    public String createCoupon(@ModelAttribute @NotNull Coupon coupon) {
        couponService.create(coupon);
        return "redirect:/admin/coupon/list";
    }

    @GetMapping("/list")
    public String couponList(Model model) {
        List<Coupon> coupons = couponService.findAll();
        model.addAttribute("coupons", coupons);
        return "admin/coupon/couponList";
    }


    @PostMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable("id") Long id) {
        couponService.delete(couponService.findById(id).get());
        return "redirect:/admin/coupon/list";
    }

    @GetMapping("/update/{id}")
    public String updateCoupon(@PathVariable("id") Long id, Model model) {
        Optional<Coupon> coupon = couponService.findById(id);
        model.addAttribute("coupon", coupon.get());
        return "admin/coupon/createCoupon";
    }

    @PostMapping("/update/{id}")
    public String updateCoupon(@PathVariable("id") Long id, Coupon coupon) {
        couponService.update(coupon);
        return "redirect:/admin/coupon/list";
    }

}



