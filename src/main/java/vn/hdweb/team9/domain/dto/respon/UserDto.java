package vn.hdweb.team9.domain.dto.respon;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class UserDto {
    private String fullName;
    private String phone;
    private String email;
    private String address;
    private String avatar;
    private String createdAt;

    public UserDto(String fullName, String email, String phone, String address, String avatar, LocalDateTime createdAt) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.avatar = avatar;
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
