package vn.hdweb.team9.controller.admin;

import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hdweb.team9.domain.dto.CouponForm;
import vn.hdweb.team9.domain.dto.CouponSearch;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.CouponStatus;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.service.CouponService;

import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/coupon")
public class CouponController {
    private final CouponService couponService;

    @Autowired
    public CouponController(CouponService couponService) { this.couponService = couponService; }

    @GetMapping("/create")
    public String createCoupon(Model model) {
        CouponForm couponForm = new CouponForm();
        model.addAttribute("couponForm", couponForm);
        return "admin/coupon/couponCreate";
    }


    @PostMapping("/create")
    public String createCoupon(@Valid CouponForm couponForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "admin/coupon/couponCreate";
        }

        try {
            Coupon coupon = Coupon.parseNew(couponForm);
            couponService.create(coupon);
            return "redirect:/admin/coupon/list";
        } catch (IllegalStateException e) {
            // Handle the exception for duplicate coupon code
            result.rejectValue("couponCode", "duplicate.couponCode", "Mã giảm giá đã tồn tại!");
            redirectAttributes.addFlashAttribute("couponForm", couponForm);
            return "admin/coupon/couponCreate";
        } catch (DateTimeException e) {
            // Handle the exception for invalid start and end dates
            result.rejectValue("startDate", "invalid.dateRange", "Ngày bắt đầu không được lớn hơn ngày kết thúc");
            redirectAttributes.addFlashAttribute("couponForm", couponForm);
            return "admin/coupon/couponCreate";
        }
    }

    @GetMapping("/list")
    public String couponList(@ModelAttribute("couponSearch")CouponSearch couponSearch, Model model) {
        List<Coupon> coupons = couponService.findCoupons(couponSearch);
        model.addAttribute("coupons", coupons);
        return "admin/coupon/couponList";
    }


    @GetMapping("/delete/{id}")
    public String deleteCoupon(@PathVariable("id") Long id) {
        couponService.deleteOne(id);
        return "redirect:/admin/coupon/list";
    }

    @GetMapping("/edit/{id}")
    public String editCoupon(@PathVariable("id") Long id, Model model) {
        Optional<Coupon> coupon = couponService.findOne(id);

        if (coupon.isEmpty()) {
            throw new IllegalStateException("This coupon does not exist");
        }

        Coupon getCoupon = coupon.get();
        CouponForm couponForm = CouponForm.parseNew(getCoupon);

        model.addAttribute("couponForm", couponForm);
        return "admin/coupon/couponEdit";
    }

    @PostMapping("/edit/{id}")
    public String updateCoupon(CouponForm couponForm,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        try {
        couponService.update(couponForm);
        return "redirect:/admin/coupon/list";
        } catch (DateTimeException e) {
            // Handle the exception for invalid start and end dates
            result.rejectValue("startDate", "invalid.dateRange", "Start date cannot be after or equal to end date");
            redirectAttributes.addFlashAttribute("couponForm", couponForm);
            return "admin/coupon/couponEdit";
        }
    }



}



