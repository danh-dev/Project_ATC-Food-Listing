package vn.hdweb.team9.domain.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SiteSettingForm {

    @NotEmpty(message = "Đây là trường bắt buộc")
    private String key;

    private String value;
}
