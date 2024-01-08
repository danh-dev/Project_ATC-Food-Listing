package vn.hdweb.team9.domain.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import vn.hdweb.team9.domain.entity.Coupon;
import vn.hdweb.team9.domain.entity.CouponStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Getter @Setter
public class CouponSearch {

    private String couponNameCode;

    //enum (DIRECT, PERCENT)
    @Enumerated(EnumType.STRING)
    private String typeCoupon;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
}
