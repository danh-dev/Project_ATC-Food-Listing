package vn.hdweb.team9.domain.dto.respon;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogResponDto {

    private Long id;
    private String blog_title;
    private String blog_content;
    private String blog_img;
    private String slug;
    private LocalDateTime createdAt;
}
