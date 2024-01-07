package vn.hdweb.team9.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;
import vn.hdweb.team9.domain.dto.CouponForm;

import java.awt.*;
import java.time.LocalDate;
import java.util.Optional;

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

    // Coupon validity period: DAY
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "start_date")
    private LocalDate startDate;

    // Coupon validity period: DAY
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "end_date")
    private LocalDate endDate;

    // enum(UPCOMING, AVAILABLE, PAST)
    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_status")
    private CouponStatus couponStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "created_at")
    private LocalDate createdAt = LocalDate.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "updated_at")
    private LocalDate updatedAt = LocalDate.now();


    //==Create Methods==//
    /**
     * Create Coupon
     */
    public static Coupon parseNew(CouponForm couponForm) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(couponForm.getCouponName());
        coupon.setCouponCode(couponForm.getCouponCode());
        coupon.setCouponDescription(couponForm.getCouponDescription());
        coupon.setTypeCoupon(couponForm.getTypeCoupon());
        coupon.setCouponValue(couponForm.getCouponValue());
        coupon.setCouponQuantity(couponForm.getCouponQuantity());
        coupon.setStartDate(couponForm.getStartDate());
        coupon.setEndDate(couponForm.getEndDate());
        coupon.updateStatus();
        return coupon;
    }

    //==Business logic methods==//
    /**
     * Update Status
     */
    public void updateStatus() {
        LocalDate currentDate = LocalDate.now();

        if (currentDate.isBefore(startDate)) {
            couponStatus = CouponStatus.UPCOMING;
        } else if (currentDate.isAfter(endDate)) {
            couponStatus = CouponStatus.PAST;
        } else {
            couponStatus = CouponStatus.AVAILABLE;
        }
        // Update the updatedAt field
        updatedAt = LocalDate.now();
    }

    /**
     * Get discount price
     */
    public int getDiscount(int totalPrice) {
        if (couponStatus != CouponStatus.AVAILABLE) {
            return 0;
        } else {
            if (typeCoupon == TypeCoupon.PERCENT) {
                return couponValue * totalPrice;
            } else if (typeCoupon == TypeCoupon.DIRECT) {
                return couponValue;
            }
        }
        return 0;
    }
}
