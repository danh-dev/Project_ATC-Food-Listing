package vn.hdweb.team9.controller.client;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hdweb.team9.domain.dto.request.SignUpDto;
import vn.hdweb.team9.domain.dto.respon.OrderListOfUserDto;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.service.imp.IUserService;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
        UserDto user =  userService.findByEmail(userEmail);
        model.addAttribute("user", user);
        return "client/users";
    }
    @PostMapping("/users/upload_avatar")
    public String uploadAvatar(@RequestParam("file") MultipartFile file, RedirectAttributes ra) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
//        Validate image
        List<String> validContentTypes = Arrays.asList("image/jpeg", "image/png");
        if(!validContentTypes.contains(file.getContentType())){
            ra.addFlashAttribute("error_upload", "Ảnh không đúng định dạng.");
            return "redirect:/users?uploadErrorType";
        }
        if(file.getSize() > 5000000){
            ra.addFlashAttribute("error_upload", "Kích thước ảnh không được vượt quá 5MB.");
            return "redirect:/users?uploadErrorSize";
        }
        userService.uploadAvatar(file, userEmail);
        ra.addFlashAttribute("success_upload", "Cập nhật avatar thành công.");
        return "redirect:/users?uploadSuccess";
    }

    @PostMapping("/users/update")
    public String update(@Valid @ModelAttribute("user") UserDto userDto, BindingResult bindingResult, RedirectAttributes ra) {
        try{
            if (bindingResult.hasErrors()) {
                ra.addFlashAttribute("error_phone", Objects.requireNonNull(bindingResult.getFieldError("phone")).getDefaultMessage());
                ra.addFlashAttribute("error_update","Cập nhật thông tin thất bại.");
                return "redirect:/users?updateError";
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userEmail = authentication.getName();
            userService.userUpdate(userDto, userEmail);
            ra.addFlashAttribute("success_update","Cập nhật thông tin thành công.");
            return "redirect:/users?updateSuccess";
        }catch (Exception e){
            ra.addFlashAttribute("error_update","Cập nhật thông tin thất bại.");
            return "redirect:/users?updateError";
        }
    }

    @PostMapping("/users/change_password")
    public String changePassword(@RequestParam("newPassword") String newPassword,
                                 @RequestParam("confirmPassword") String confirmPassword,
                                 @RequestParam("oldPassword") String oldPassword) {
        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/users?confirmPasswordError";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userService.checkExistPassword(oldPassword,userEmail)) {
            return "redirect:/users?oldPasswordError";
        }
        if(userService.checkExistPassword(newPassword,userEmail)){
            return "redirect:/users?existPasswordError";
        }
        userService.changePassword(newPassword, userEmail);
        return "redirect:/users?changePasswordSuccess";
    }

    @GetMapping("/users/orders")
    public String orderListOfUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        UserDto user =  userService.findByEmail(userEmail);
        model.addAttribute("user", user);
        List<OrderListOfUserDto> orders = userService.orderListOfUser(userEmail);
        model.addAttribute("orders", orders);
        return "client/user_orders";
    }

}
