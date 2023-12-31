package vn.hdweb.team9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CouponController {

    @GetMapping("/coupons/save")
    public String saveCoupon() { return "coupons/saveCoupon"; }

}
