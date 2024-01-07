package vn.hdweb.team9.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    
    @GetMapping("/list")
    public String categoryList() {
        return "admin/category/categoryList";
    }
    
    @GetMapping("/new")
    public String createCategory() {
        return "admin/category/createCategory";
    }
}
