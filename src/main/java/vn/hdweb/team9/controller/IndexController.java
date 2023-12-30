package vn.hdweb.team9.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class IndexController {

    public String index() {
        return "index";
    }
}
