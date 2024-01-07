package vn.hdweb.team9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping(value = {"", "/","/dashboard"})
    public String home() {
        return "admin/dashboard";
    }
}
