package vn.hdweb.team9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ClientController {

    @GetMapping(value = {"", "/","/home","/index","/trang-chu","index.html","home.html","trang-chu.html"})
    public String home() {
        return "client/index";
    }
    @GetMapping("/contact")
    public String contact() {
        return "client/contact";
    }

    @RequestMapping("/404")
    public String notFound() {
        return "client/404";
    }
}
