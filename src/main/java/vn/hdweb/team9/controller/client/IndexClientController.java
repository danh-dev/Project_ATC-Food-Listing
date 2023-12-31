package vn.hdweb.team9.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexClientController {
    
    @GetMapping("/")
    public String index() {
        return "client/index";
    }
}
