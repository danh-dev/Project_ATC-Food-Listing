package vn.hdweb.team9.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import vn.hdweb.team9.domain.dto.Cart;
import vn.hdweb.team9.domain.dto.respon.UserDto;
import vn.hdweb.team9.service.CartService;
import vn.hdweb.team9.service.imp.IUserService;

@ControllerAdvice
@RequiredArgsConstructor
public class UserInfoController {

    private final IUserService userService;
    private final CartService cartService;

    @ModelAttribute("userInfo")
    public void userInfo(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserDto userDto = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName()).convertUserDto();
            model.addAttribute("userInfo", userDto);
        }
    }

    @ModelAttribute("cartInfo")
    public Cart  cartInfo(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            Cart cart = cartService.getCart(session);
            if (cart != null) {
                return cart;
            }
        }
        return null;
    }
}
