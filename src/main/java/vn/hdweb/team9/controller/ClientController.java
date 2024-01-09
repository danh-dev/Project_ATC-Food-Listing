package vn.hdweb.team9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.dto.respon.BlogResponDto;
import vn.hdweb.team9.service.BlogService;

import java.util.List;

@Controller
@RequestMapping("/")
public class ClientController {
    private final BlogService blogService;

    public ClientController(BlogService blogService) {
        this.blogService = blogService;
    }

    @GetMapping(value = {"", "/","/home","/index","/trang-chu","index.html","home.html","trang-chu.html"})
    public String home(Model model) {
        List<BlogResponDto> blogs = blogService.findLimitOrderDate();
        model.addAttribute("blogs",blogs);
        return "client/index";
    }

    @GetMapping("/category_demo")
    public String category_demo() {
        return "client/category_page";
    }

    @GetMapping("/restaurant_demo")
    public String restaurant_demo() {
        return "client/restaurant_page";
    }

    @GetMapping("/food_demo")
    public String food_demo() {
        return "client/food_page";
    }

//    @GetMapping("/blog_demo")
//    public String blog_demo (Model model) {
//        List<BlogResponDto> blogs = blogService.findAll();
//        model.addAttribute("blogs",blogs);
//        return "client/blogs";
//    }

    @RequestMapping("/404")
    public String notFound() {
        return "client/404";
    }
}
