package vn.hdweb.team9.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class CategoryDTO {
    private Long id;
    
    @NotBlank
    @Size(min = 5, message = "Category name must be at least 5 chars!")
    private String categoryName;
    
    private MultipartFile imageFile;
}
