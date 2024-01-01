package vn.hdweb.team9.domain.dto.request;

import lombok.Data;

@Data
public class SignUpDto {
    private String email;
    private String password;
    private String fullName;
}
