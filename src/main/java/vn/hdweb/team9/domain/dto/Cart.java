package vn.hdweb.team9.domain.dto;

import lombok.Data;
import vn.hdweb.team9.domain.entity.Food;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cart {
    private List<ItemCart> items = new ArrayList<>();
    private String Coupon;
    private String Restaurant;

    public ItemCart findItemByFoodId(Long foodId) {
        for (ItemCart item : items) {
            if (item.getFood().getId().equals(foodId)) {
                return item;
            }
        }
        return null;
    }

    public void add(Food food, int quantity) {
        if (Restaurant != null && !Restaurant.equals(food.getRestaurant().getRestaurantName())) {
            throw new RuntimeException("Không thể đặt món ăn từ nhiều nhà hàng khác nhau");
        }
        ItemCart item = new ItemCart();
        Restaurant = food.getRestaurant().getRestaurantName();
        item.setFood(food);
        item.setQuantity(quantity);
        items.add(item);
    }

    public void remove(Long foodId) {
        items.removeIf(item -> item.getFood().getId().equals(foodId));
    }

    public void update(Long foodId, int quantity) {
        items.forEach(item -> {
            if (item.getFood().getId().equals(foodId)) {
                item.setQuantity(quantity);
            }
        });
    }

    public int getTotalBill() {
        int total = 0;
        for (ItemCart item : items) {
            total += item.getFood().getPrice() * item.getQuantity();
        }
        return total;
    }
}