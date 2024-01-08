package vn.hdweb.team9.controller.client;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hdweb.team9.domain.dto.Cart;
import vn.hdweb.team9.service.CartService;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping(value = {"", "/"})
    public String cart(HttpSession session, Model model) {
        Cart cart = cartService.getCart(session);
        model.addAttribute("cart", cart);
        return "client/cart";
    }

    @PostMapping("/add/{foodId}")
    public String addToCart(@PathVariable Long foodId, HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            return "redirect:/login";
        }
        cartService.addToCart(request.getSession(), foodId, 1);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeCartItem(@RequestParam Long foodId, HttpSession session) {
        cartService.removeItem(session, foodId);
        return "redirect:/cart";
    }

    @PostMapping("/update/{foodId}")
    public String updateCartItem(@PathVariable Long foodId, @RequestParam int quantity, HttpSession session) {
        cartService.updateItem(session, foodId, quantity);
        return "redirect:/cart";
    }
    @PostMapping("/apply-coupon")
    public String addCoupon(@RequestParam String coupon, HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = cartService.getCart(session);
        if (coupon.equals("ABC")) {
            cart.setCoupon(coupon);
            session.setAttribute("cart", cart);
            redirectAttributes.addFlashAttribute("apply_success", "Thêm mã giảm giá thành công");
        } else {
            redirectAttributes.addFlashAttribute("apply_error", "Mã giảm giá không hợp lệ");
        }
        return "redirect:/cart";
    }
    @PostMapping("/remove-coupon")
    public String removeCoupon(HttpSession session) {
        Cart cart = cartService.getCart(session);
        cart.setCoupon(null);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }
}
