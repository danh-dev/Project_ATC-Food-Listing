package vn.hdweb.team9.domain.dto;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import vn.hdweb.team9.domain.entity.TypeCoupon;

import java.time.LocalDate;


@Getter @Setter
public class CouponForm {

    private String couponCode;

    private String couponName;

    private String couponDescription;

    //enum (DIRECT, PERCENT)
    @Enumerated(EnumType.STRING)
    private TypeCoupon typeCoupon;

    private int couponValue;

    private int couponQuantity;

    private LocalDate startDate;

    private LocalDate endDate;
}
