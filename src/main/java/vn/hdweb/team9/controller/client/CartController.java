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
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.service.CartService;
import vn.hdweb.team9.service.CouponService;

import java.util.Optional;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;
    private final CouponService couponService;
    @GetMapping(value = {"", "/"})
    public String cart(HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            redirectAttributes.addFlashAttribute("request_login","Bạn phải đăng nhập để sử dụng chức năng này");
            return "redirect:/login";
        }
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
    public String addCoupon(@RequestParam String coupon, HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        Cart cart = cartService.getCart(session);
        Optional<Coupon> thisCoupon = couponService.findByCouponCode(coupon);
        if (thisCoupon.isEmpty()) {
            redirectAttributes.addFlashAttribute("apply_error", "Mã giảm giá không tồn tại");
            return "redirect:/cart";
        }
        if(thisCoupon.get().getOrderDiscount(cart.getTotalBill()) == 0) {
            redirectAttributes.addFlashAttribute("apply_error", "Mã giảm giá không áp dụng cho đơn hàng này");
            return "redirect:/cart";
        }
        cart.setCoupon(thisCoupon.get());
        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("apply_success", "Thêm mã giảm giá thành công");
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
