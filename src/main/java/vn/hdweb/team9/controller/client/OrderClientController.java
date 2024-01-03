package vn.hdweb.team9.controller.client;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.dto.order.OrderDetailRequestDto;
import vn.hdweb.team9.domain.dto.order.OrderRequestDto;
import vn.hdweb.team9.domain.dto.order.OrderResponseDto;
import vn.hdweb.team9.service.*;

import java.util.Collections;


@Controller
@RequestMapping("/client/orders")
public class OrderClientController {

    private final OrderService orderService;
    @Autowired
    public OrderClientController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping("{orderId}")
    public String orderClientById(@PathVariable Long orderId, Model model){
        OrderResponseDto order = orderService.orderOneResponse(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        model.addAttribute("order", order);
        return "client/orders/orderDetail";
    }
    @PostMapping("/create")
    public String createOrder(@ModelAttribute OrderRequestDto orderRequestDto,
                              @ModelAttribute OrderDetailRequestDto orderDetailRequestDto,
                              HttpSession session
    ) {
        try {
            // Retrieve userId from the session (assuming you store it in the session under the key "userId")
            Long userId = (Long) session.getAttribute("userId");
            // Set the userId in the OrderRequestDto
            orderRequestDto.setUserId(userId);
            // Set orderDetailRequestDto in the OrderRequestDto
            orderRequestDto.setOrderDetails(Collections.singletonList(orderDetailRequestDto));
            Long orderId = orderService.placeOrder(orderRequestDto);

            // Redirect to the order details page for the newly created order or page user's order
            // Retrieval list user's order
            return "redirect:/client/orders/" + orderId;
        } catch (Exception e) {
            // If there's an error, you might want to handle it, log it, or redirect to an error page
            return "redirect:/error";
        }
    }
    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId){
        try {
            orderService.cancelOrder(orderId);

            // Redirect to the list of user's orders or another appropriate page
            return "redirect:/client/orders";
        } catch (Exception e) {
            // If there's an error, you might want to handle it, log it, or redirect to an error page
            return "redirect:/error";
        }
    }

}
