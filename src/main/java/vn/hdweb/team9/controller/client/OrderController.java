package vn.hdweb.team9.controller.client;


import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.service.imp.IOrderService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final IOrderService orderService;

    @PostMapping("/create")
    public String createOrder(HttpSession session, @RequestParam String address, @RequestParam String note, @RequestParam String paymentMethod) {

        Order order = orderService.createOrder(session, address, note, paymentMethod);

        return "redirect:/users/orders/detail/" + order.getId();
    }

}
