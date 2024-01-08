package vn.hdweb.team9.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.dto.Cart;
import vn.hdweb.team9.domain.dto.CouponDto;
import vn.hdweb.team9.domain.entity.*;
import vn.hdweb.team9.repository.interfaces.IOrderDetailRepository;
import vn.hdweb.team9.repository.interfaces.IOrderRepository;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;
import vn.hdweb.team9.repository.interfaces.IUserRepository;
import vn.hdweb.team9.service.imp.IOrderService;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService  {

    private final IOrderRepository orderRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final IUserRepository userRepository;
    private final IRestaurantRepository restaurantRepository;
    private final CouponService couponService;
    private final CartService cartService;

    @Override
    public Order createOrder(HttpSession session, String address, String note, String paymentMethod) {
        Cart cart = cartService.getCart(session);
        Order order = new Order();
        User user = getUser(session);
        user.getListOrder().add(order);
        Restaurant restaurant = restaurantRepository.findByRestaurantName(cart.getRestaurant()).get();
        restaurant.getListOrder().add(order);
        order.setDeliveryAddress(address);
        if(paymentMethod.equalsIgnoreCase("vnpay")) {
            order.setStatus(OrderStatus.WAITING);
        } else {
            order.setStatus(OrderStatus.PROCESSING);
        }
        order.setOrderNote(note);
        CouponDto couponDto = new CouponDto();
        Coupon coupon = couponService.findByCouponCode(cart.getCoupon()).get();
        couponDto.setCouponCode(coupon.getCouponCode());
        couponDto.setTypeCoupon(coupon.getTypeCoupon());
        couponDto.setCouponValue(coupon.getCouponValue());
        couponDto.setOrderDiscount(coupon.getOrderDiscount(cart.getTotalBill()));
        order.setCouponDto(couponDto);
        order.setRatingRestaurant(false);
        order.setTotalBill(cart.getTotalBill() - couponDto.getOrderDiscount());
        order.setRestaurant(restaurant);
        order.setUser(user);
        for (int i = 1; i < cart.getItems().size(); i++) {
            OrderDetail orderDetail = OrderDetail.createOrderDetail(cart.getItems().get(i), order);
            orderDetailRepository.save(orderDetail);
            order.getOrderItems().add(orderDetail);
        }
        orderRepository.save(order);
        return order;
    }

    private User getUser(HttpSession session) {
        Authentication authentication = (Authentication) session.getAttribute("authentication");
        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        return user;
    }

}
