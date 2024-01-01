package vn.hdweb.team9.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import vn.hdweb.team9.domain.dto.request.SignUpDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.service.imp.IUserService;

@Controller
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "client/login";
    }

    @GetMapping("/register")
    public String showRegisterUser() {
        return "client/register";
    }

    @ModelAttribute("user")
    public SignUpDto signUpDto() {
        return new SignUpDto();
    }

    @PostMapping("/register")
    public String doRegisterUser(@ModelAttribute("user") SignUpDto signUpDto) {
        userService.save(signUpDto);
        return "redirect:/users?success";
    }

    @GetMapping("/logout")
    public String logOutCallback() {
        return "redirect:/users";
    }

    @PostMapping("/logout")
    public String logout() {
        return "redirect:/login";
    }

    @RequestMapping("/users")
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UserDto user =  userService.findByEmail(userEmail);
        model.addAttribute("user", user);

        return "client/users";
    }

}
