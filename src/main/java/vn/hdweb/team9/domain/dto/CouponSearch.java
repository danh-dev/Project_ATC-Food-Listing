package vn.hdweb.team9.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.hdweb.team9.domain.entity.CouponStatus;

import java.time.LocalDate;

@Getter @Setter
public class CouponSearch {

    private String couponName;

    private String couponCode;

    //enum (DIRECT, PERCENT)
    @Enumerated(EnumType.STRING)
    private String typeCoupon;

    private int couponValue;

    private int couponQuantity;

    // enum(UPCOMING, AVAILABLE, PAST)
    @Enumerated(EnumType.STRING)
    private CouponStatus couponStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
