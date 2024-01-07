package vn.hdweb.team9.domain.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpDto {

    @NotEmpty(message = "Vui lòng nhập email")
    @Email(message = "Vui lòng nhập đúng định dạng email")
    private String email;
    @NotEmpty(message = "Vui lòng nhập mật khẩu")
    @Pattern(regexp = "^(?=.*[@$!%*#?&])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d@$!%*#?&]{8,}$", message = "Mật khẩu phải có ít nhất 8 ký tự, bao gồm chữ hoa, chữ thường, số và ký tự đặc biệt")
    private String password;
    @NotEmpty(message = "Vui lòng nhập họ và tên")
    private String fullName;
}
