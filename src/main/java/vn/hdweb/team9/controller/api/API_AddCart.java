/*
package vn.hdweb.team9.controller.api;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.hdweb.team9.domain.dto.Cart;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.Restaurant;
import vn.hdweb.team9.service.FoodService;
import vn.hdweb.team9.utility.CustomRespon;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class API_AddCart {

    private HttpSession session;
    private FoodService foodService;

    @PostMapping("/api/add-cart/{id}")
    public CustomRespon addCart(@PathVariable Long id) {
        try{
            Optional<Food> food = foodService.findById(id);

            if(food.isEmpty()) {
                return new CustomRespon("Product not found", null, 404);
            }
            List<Cart> cartInfo = (List<Cart>) session.getAttribute("cartInfo");
            if (cartInfo == null) {
                cartInfo = new ArrayList<>();
                cartInfo.add(new Cart(id));
            } else {
                boolean found = false;
                for (Cart cartDto : cartInfo) {
                    Restaurant Res_name = foodService.findById(cartDto.getProductId()).get().getRestaurant();
                    Restaurant Res_name2 = foodService.findById(id).get().getRestaurant();
                    if (!Res_name.equals(Res_name2)) {
                        return new CustomRespon("Add cart fail", null, 409);
                    }
                    if (cartDto.getProductId().equals(id)) {
                        cartDto.incrementQuantity();
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    cartInfo.add(new Cart(id));
                }
            }
            session.setAttribute("cartInfo", cartInfo);
            return new CustomRespon("Add cart success", cartInfo, 200);
        }catch(Exception e){
            return new CustomRespon("Add cart fail", null, 500);
        }
    }
}
*/
