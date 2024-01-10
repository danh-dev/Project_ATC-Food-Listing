package vn.hdweb.team9.domain.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter @Setter
public class FoodDTO {
    
    private Long id;
    
    @NotBlank
    @Size(min = 5, message = "Food name must not be empty!")
    private String foodName;
    
    private String slug;
    
    @NotBlank
    @Size(min = 10, message = "Description must not be at least 10 chars!")
    private String description;
    
    @NotNull(message = "Price is required")
    @Min(value= 0, message="Price must be positive number")
    private int price;
    
    private MultipartFile imageFile;
    
    @Min(value= 0, message="Waiting time must be positive number")
    private int timeWait;
    
    @NotNull(message = "Choose category !")
    private Long categoryId;
}
