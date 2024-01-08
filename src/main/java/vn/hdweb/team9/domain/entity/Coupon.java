package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Column(name = "coupon_code")
    private String couponCode;

    @Column(name = "coupon_name")
    private String couponName;

    @Column(name = "coupon_description")
    private String couponDescription;

    //enum (DIRECT, PERCENT)
    @Enumerated(EnumType.STRING)
    @Column(name = "type_coupon")
    private TypeCoupon typeCoupon;

    @Column(name = "coupon_value")
    private int couponValue;

    @Column(name = "coupon_quantity")
    private int couponQuantity;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "create_at")
    private LocalDate createdAt = LocalDate.now();
}
