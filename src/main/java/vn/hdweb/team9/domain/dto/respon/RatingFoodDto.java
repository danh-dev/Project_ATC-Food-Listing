package vn.hdweb.team9.domain.dto.respon;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.domain.entity.User;

import java.time.LocalDateTime;


@Data
public class RatingFoodDto {
    private Long id;

    private String content;

    private int rateStar;

    private LocalDateTime createdAt;

    private String foodName;

    private String userFullName;

    private String userAvatar;

}
