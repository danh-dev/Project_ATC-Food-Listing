package vn.hdweb.team9.domain.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageDto {
    private MultipartFile file;
}
