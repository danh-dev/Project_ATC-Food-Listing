package vn.hdweb.team9.domain.dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.CouponStatus;
import vn.hdweb.team9.domain.entity.TypeCoupon;

import java.awt.*;
import java.time.LocalDate;


@Getter @Setter
@RequiredArgsConstructor
public class CouponForm {

    private Long id;

    @Size(min = 5, message = "Name must contain at least 5 characters")
    @NotEmpty(message = "This field is required")
    private String couponName;

    @Size(min = 5, message = "Code must contain at least 5 characters")
    @NotEmpty(message = "This field is required")
    private String couponCode;

    private String couponDescription;

    //enum (DIRECT, PERCENT)
    @NotNull(message = "This field is required")
    @Enumerated(EnumType.STRING)
    private TypeCoupon typeCoupon;

    @Min(value = 1, message = "Coupon value is required and greater than 0")
    private int couponValue;

    @Min(value = 1, message = "Coupon quantity is required and greater than 0")
    @NotNull(message = "Coupon quantity is required")
    private int couponQuantity;

    @NotNull(message = "Start date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;


    @NotNull(message = "End date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;


    //==Create Methods==//
    /**
     * Create New
     */
    // Create New method
    public static CouponForm parseNew(Coupon coupon) {
        CouponForm couponForm = new CouponForm();
        couponForm.setId(coupon.getId());
        couponForm.setCouponName(coupon.getCouponName());
        couponForm.setCouponCode(coupon.getCouponCode());
        couponForm.setCouponDescription(coupon.getCouponDescription());
        couponForm.setTypeCoupon(coupon.getTypeCoupon());
        couponForm.setCouponValue(coupon.getCouponValue());
        couponForm.setCouponQuantity(coupon.getCouponQuantity());
        couponForm.setStartDate(coupon.getStartDate());
        couponForm.setEndDate(coupon.getEndDate());
        return couponForm;
    }

}
