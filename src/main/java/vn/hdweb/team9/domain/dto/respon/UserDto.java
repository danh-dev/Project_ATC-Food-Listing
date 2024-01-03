package vn.hdweb.team9.domain.dto.respon;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String fullName;
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không đúng định dạng.")
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String role;
    private LocalDateTime createdAt;
}
