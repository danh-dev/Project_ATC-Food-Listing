package vn.hdweb.team9.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class CategoryDTO {
    private Long id;
    
    @NotNull()
    @Size(min = 5, message = "Category name must not be empty!")
    private String categoryName;
    
    private MultipartFile imageFile;
}
