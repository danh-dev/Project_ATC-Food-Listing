package vn.hdweb.team9.controller.admin;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.User;
import vn.hdweb.team9.service.imp.IOrderService;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final IOrderService orderService;

    @RequestMapping(value = {"", "/","/list"})
    public String ordersList(Model model) {

        List<Order> orders = orderService.findAll();
        orders.sort((o1, o2) -> o2.getId().compareTo(o1.getId()));
        model.addAttribute("orders", orders);
        return "admin/orders_list";
    }

    @GetMapping("/{orderId}")
    public String orderDetail(@PathVariable("orderId") Long orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "admin/detail_order";
    }
}
