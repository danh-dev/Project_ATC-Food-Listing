package vn.hdweb.team9.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class IndexAdminController {
    
    @GetMapping("")
    public String index() {
        return "admin/index";
    }
}
