package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import vn.hdweb.team9.domain.dto.CouponDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="restaurant_id")
    private Restaurant restaurant;

    @Column(name = "delivery_address")
    private String deliveryAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderItems = new ArrayList<>();

    @Column(name = "order_note")
    private String orderNote;

    @Column(name = "total_bill")
    private int totalBill;

    @Column(name = "is_rating_restaurant")
    private boolean isRatingRestaurant;

    @Column(name = "coupon_value")
    private String couponDto;

    //enum (PROCESSING, WAITING , SHIPPING, DELIVERED, CANCEL)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "update_at")
    private LocalDateTime updatedAt = LocalDateTime.now();

    public boolean getRatingRestaurant() {
        return isRatingRestaurant;
    }

    public void setCouponDto(CouponDto couponDto)  {
        try{
            this.couponDto = couponDto.toJson();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CouponDto getCouponDto() {
        try {
            return CouponDto.fromJson(this.couponDto);
        } catch (IOException e) {
           return null;
        }
    }

    public void cancelOrder() {
        this.status = OrderStatus.CANCEL;
    }
}
