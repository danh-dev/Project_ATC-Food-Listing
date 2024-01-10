package vn.hdweb.team9.controller.client;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.repository.interfaces.IUserRepository;
import vn.hdweb.team9.service.imp.IOrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final IOrderService orderService;

    private final IUserRepository userRepository;

    @PostMapping("/create")
    public String createOrder(HttpSession session, @RequestParam String address, @RequestParam String note, @RequestParam String paymentMethod, @RequestParam String addressUse) {

        if(addressUse.equalsIgnoreCase("user")) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = userRepository.findByEmail(authentication.getName());
            address = user.getAddress();
        }

        Order order = orderService.createOrder(session, address, note, paymentMethod);
        return "redirect:/users/orders/" + order.getId();
    }

    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/users";
    }

}
