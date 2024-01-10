package vn.hdweb.team9.domain.dto.respon;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    @NotNull(message = "Tên đăng nhập không được để trống.")
    @Pattern(regexp = "^[a-zA-Z]+(?:\\s[a-zA-Z]+)*$", message = "Tên đăng nhập không đúng định dạng.")
    private String fullName;
    @NotNull(message = "Số điện thoại không được để trống.")
    @Pattern(regexp = "^[0-9]{10}$", message = "Số điện thoại không đúng định dạng.")
    private String phone;
    @NotNull(message = "Email không được để trống.")
    @Email(message = "Email không đúng định dạng.")
    private String email;
    private String address;
    private String avatar;
    private String role;
    private LocalDateTime createdAt;
}
