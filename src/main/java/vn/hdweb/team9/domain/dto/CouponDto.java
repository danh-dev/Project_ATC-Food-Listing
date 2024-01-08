package vn.hdweb.team9.domain.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import vn.hdweb.team9.domain.entity.TypeCoupon;

import java.io.IOException;

@Data
public class CouponDto {
    private String couponCode;

    @Enumerated(EnumType.STRING)
    private TypeCoupon typeCoupon;

    private int couponValue;

    private int orderDiscount;

    /*https://stackoverflow.com/questions/15786129/converting-java-objects-to-json-with-jackson*/
    public String toJson() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

    public static CouponDto fromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, CouponDto.class);
    }
}
