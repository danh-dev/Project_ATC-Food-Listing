package vn.hdweb.team9.service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.hdweb.team9.domain.dto.Cart;
import vn.hdweb.team9.domain.dto.ItemCart;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.repository.interfaces.IFoodRepository;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final IFoodRepository foodRepository;

    public void addToCart(HttpSession session, Long foodId, int quantity) throws RuntimeException {
        Cart cart = getCart(session);
        Optional<Food> food = foodRepository.findById(foodId);
        if (food.isEmpty()) {
            throw new RuntimeException("Không tìm thấy món ăn với id = " + foodId);
        }
        ItemCart itemExist = cart.findItemByFoodId(foodId);
        if(itemExist != null) {
            itemExist.incrementQuantity();
        } else {
            cart.add(food.get(), quantity);
        }
        session.setAttribute("cart", cart);
    }

    public void removeItem(HttpSession session, Long foodId) {
        Cart cart = getCart(session);
        cart.remove(foodId);
        session.setAttribute("cart", cart);
    }

    public void updateItem(HttpSession session, Long foodId, int quantity) {
        Cart cart = getCart(session);
        cart.update(foodId, quantity);
        session.setAttribute("cart", cart);
    }

    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }

    public void addCoupon(HttpSession session, Coupon coupon) {
        Cart cart = getCart(session);
        cart.setCoupon(coupon);
        session.setAttribute("cart", cart);
    }

    public void removeCart(HttpSession session) {
        session.removeAttribute("cart");
    }
}
