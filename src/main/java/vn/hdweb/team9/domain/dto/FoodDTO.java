package vn.hdweb.team9.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class FoodDTO {
    
    private Long id;
    
    @NotNull()
    @Size(min = 5, message = "Food name must not be empty!")
    private String foodName;
    
    private String slug;
    
    private String description;
    
    private int price;
    
    private MultipartFile image;
    
    private int timeWait;
}
