package vn.hdweb.team9.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    
    @GetMapping("/list")
    public String categoryList() {
        return "admin/user/userList";
    }
    
    @GetMapping("/new")
    public String createCategory() {
        return "admin/user/createUser";
    }
}
