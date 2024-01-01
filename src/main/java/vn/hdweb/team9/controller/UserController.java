package vn.hdweb.team9.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vn.hdweb.team9.domain.dto.request.ImageDto;
import vn.hdweb.team9.domain.dto.request.SignUpDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.service.imp.IUserService;

@Controller
@Slf4j
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
    public String showRegisterUser(Model model) {
        model.addAttribute("user", new SignUpDto());
        return "client/register";
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

    @RequestMapping(value = {"/users","/users/"})
    public String user(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        log.info("User info 1 : {}", userService.findByEmail(userEmail));
        UserDto user =  userService.findByEmail(userEmail);
        log.info("User info 2 : {}", user);
        model.addAttribute("user", user);
        return "client/users";
    }
    @PostMapping("/users/upload_avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file) {
        log.info("File name: {}", file.getOriginalFilename());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        userService.uploadAvatar(file, userEmail);
        return "redirect:/users?uploadSuccess";
    }
    @PostMapping("/users/update")
    public String update(@ModelAttribute("user") UserDto userDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        userService.userUpdate(userDto, userEmail);
        return "redirect:/users?updateSuccess";
    }


}
