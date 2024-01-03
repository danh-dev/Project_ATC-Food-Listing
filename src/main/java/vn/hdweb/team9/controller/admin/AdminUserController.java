package vn.hdweb.team9.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.service.imp.IUserService;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final IUserService userService;


    @RequestMapping(value = {"", "/","/list"})
    public String usersList(Model model) {

        model.addAttribute("users", userService.findAll());

        return "admin/users_list";
    }
}
