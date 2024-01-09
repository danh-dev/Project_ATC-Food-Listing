package vn.hdweb.team9.service.imp;

import jakarta.servlet.http.HttpSession;
import vn.hdweb.team9.domain.entity.Order;
import vn.hdweb.team9.domain.entity.User;

import java.util.List;

public interface IOrderService {

        Order createOrder(HttpSession session, String address, String note, String paymentMethod);

        List<Order> orderOfUser(User user);
}
