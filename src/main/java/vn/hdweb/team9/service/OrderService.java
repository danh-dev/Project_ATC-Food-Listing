package vn.hdweb.team9.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.dto.Cart;
import vn.hdweb.team9.domain.dto.CouponDto;
import vn.hdweb.team9.domain.dto.ItemCart;
import vn.hdweb.team9.domain.entity.*;
import vn.hdweb.team9.repository.interfaces.IOrderDetailRepository;
import vn.hdweb.team9.repository.interfaces.IOrderRepository;
import vn.hdweb.team9.repository.interfaces.IRestaurantRepository;
import vn.hdweb.team9.repository.interfaces.IUserRepository;
import vn.hdweb.team9.service.imp.IOrderService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final IOrderDetailRepository orderDetailRepository;
    private final IUserRepository userRepository;
    private final IRestaurantRepository restaurantRepository;
    private final CartService cartService;
    private final CouponService couponService;

    @Override
    public Order createOrder(HttpSession session, String address, String note, String paymentMethod) {
        Cart cart = null;
        try {
            cart = cartService.getCart(session);
            log.info("Cart: {}", cart);
            Order order = new Order();
            User user = getUser();
            user.getListOrder().add(order);
            Restaurant restaurant = restaurantRepository.findByRestaurantName(cart.getRestaurant()).get();
            restaurant.getListOrder().add(order);
            order.setDeliveryAddress(address);
            if (paymentMethod.equalsIgnoreCase("vnpay")) {
                order.setStatus(OrderStatus.WAITING);
            } else {
                order.setStatus(OrderStatus.PROCESSING);
            }
            order.setOrderNote(note);
            order.setTotalBill(cart.getTotalBill());
            if (cart.getCoupon() != null) {
                CouponDto couponDto = new CouponDto();
                Coupon coupon = cart.getCoupon();
                couponDto.setCouponCode(coupon.getCouponCode());
                couponDto.setTypeCoupon(coupon.getTypeCoupon());
                couponDto.setCouponValue(coupon.getCouponValue());
                couponDto.setOrderDiscount(coupon.getOrderDiscount(cart.getTotalBill()));
                order.setCouponDto(couponDto);
                order.setTotalBill(cart.getTotalBill() - couponDto.getOrderDiscount());
                couponService.findByCouponCode(coupon.getCouponCode()).get().decreaseCouponQuantity();
            }
            order.setRatingRestaurant(false);
            order.setRestaurant(restaurant);
            order.setUser(user);
            for (ItemCart item : cart.getItems()) {
                OrderDetail orderDetail = OrderDetail.createOrderDetail(item, order);
                orderDetailRepository.save(orderDetail);
                order.getOrderItems().add(orderDetail);
            }
            log.info("Order log ne: {}", order.getOrderItems().size());
            orderRepository.save(order);
            return order;
        } catch (Exception e) {
            assert cart != null;
            if (cart.getCoupon() != null) {
                couponService.findByCouponCode(cart.getCoupon().getCouponCode()).get().increaseCouponQuantity();
            }
            log.error("Error create order: {}", e.getMessage());
            throw new RuntimeException("Error create order");
        } finally {
            cartService.removeCart(session);
        }
    }

    @Override
    public List<Order> orderOfUser(User user) {
        return orderRepository.findAllByUser(user);
    }


    private User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName());
        if (user == null) {
            throw new RuntimeException("Không tìm thấy người dùng");
        }
        return user;
    }

}
