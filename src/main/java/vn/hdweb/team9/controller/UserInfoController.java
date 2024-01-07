package vn.hdweb.team9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.service.imp.IUserService;

@ControllerAdvice
@RequiredArgsConstructor
public class UserInfoController {

    private final IUserService userService;

    @ModelAttribute
    public void userInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDto userDto = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            model.addAttribute("userInfo", userDto);
        }
    }
}
