package vn.hdweb.team9.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class CouponSearch {

    private String couponCode;

    //enum (DIRECT, PERCENT)
    @Enumerated(EnumType.STRING)
    private String typeCoupon;

    private int couponValue;

    private LocalDate startDate;

    private LocalDate endDate;


}
