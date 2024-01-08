package vn.hdweb.team9.domain.dto;

import lombok.Data;
import vn.hdweb.team9.domain.entity.Food;

@Data
public class ItemCart {
    private Food food;
    private int quantity;

    public void incrementQuantity() {
        this.quantity++;
    }
}


