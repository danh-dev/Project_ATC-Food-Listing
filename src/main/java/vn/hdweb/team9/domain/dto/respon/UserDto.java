package vn.hdweb.team9.domain.dto.respon;
import lombok.Data;
import vn.hdweb.team9.domain.entity.Role;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class UserDto {
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String role;
    private String createdAt;
}
