package vn.hdweb.team9.domain.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BlogRepuestDto {
    private Long blogId;
    private String blog_title;
    private String blog_content;
    private MultipartFile blog_img;
}
