package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderItems = new ArrayList<>();

    @Column(name = "order_note")
    private String orderNote;

    @Column(name = "total_bill")
    private int totalBill;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "is_rating_restaurant")
    private boolean isRatingRestaurant;

    //enum (PROCESSING, SHIPPING, DELIVERED, CANCEL)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status;

    @Column(name = "create_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "update_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
}
