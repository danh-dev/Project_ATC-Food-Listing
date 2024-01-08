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

    @Size(min = 5, message = "Tên mã phải chứa ít nhất 5 kí tự")
    @NotEmpty(message = "Đây là trường bắt buộc")
    private String couponName;

    @Size(min = 5, message = "Mã giảm giá phải chứa ít nhất 5 kí tự")
    @NotEmpty(message = "Đây là trường bắt buộc")
    private String couponCode;

    private String couponDescription;

    //enum (DIRECT, PERCENT)
    @NotNull(message = "Đây là trường bắt buộc")
    @Enumerated(EnumType.STRING)
    private TypeCoupon typeCoupon;

    @Min(value = 1, message = "Giá trị mã phải lớn hơn 0")
    private int couponValue;

    @Min(value = 1, message = "Số lượng mã phải lớn hơn 0")
    @NotNull(message = "Đây là trường bắt buộc")
    private int couponQuantity;

    @NotNull(message = "Ngày bắt đầu là bắt buộc")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;


    @NotNull(message = "Ngày kết thúc là bắt buộc")
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
