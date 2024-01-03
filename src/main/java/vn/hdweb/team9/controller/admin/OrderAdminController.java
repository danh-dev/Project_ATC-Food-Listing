package vn.hdweb.team9.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.hdweb.team9.domain.dto.order.OrderResponseDto;
import vn.hdweb.team9.domain.dto.order.OrderUpdateRequestDto;
import vn.hdweb.team9.repository.OrderRepository;
import vn.hdweb.team9.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/admin/orders")
public class OrderAdminController {

    private final OrderService orderService;
    private OrderRepository orderRepository;

    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Get all orders
    @GetMapping("/all")
    public String getAllOrders(Model model) {
        List<OrderResponseDto> orders = orderService.orderList();
        model.addAttribute("orders", orders);
        return "admin/orders/orderList";
    }

    // Show/edit order by ID

    @GetMapping("/{orderId}")
    public String getOrderById(@PathVariable Long orderId, Model model) {
        OrderResponseDto order = orderService.orderOneResponse(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        model.addAttribute("order", order);
        return "admin/orders/orderDetail";
    }

    // Edit order of status
    @PostMapping("/{orderId}/update-status")
    public ResponseEntity<String> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderUpdateRequestDto orderUpdateDto) {
        try {
            orderService.updateOrderStatus(orderId, orderUpdateDto);
            return ResponseEntity.ok("Order status updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update order status");
        }
    }

}
