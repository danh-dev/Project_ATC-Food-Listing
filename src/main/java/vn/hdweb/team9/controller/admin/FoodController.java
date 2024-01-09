package vn.hdweb.team9.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.hdweb.team9.domain.dto.FoodDTO;
import vn.hdweb.team9.domain.entity.Food;
import vn.hdweb.team9.service.FoodService;
import vn.hdweb.team9.utility.UploadFileUtil;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/food")
public class FoodController {
    
    private FoodService foodService;
    
    @Autowired
    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }
    
    @GetMapping("/list")
    public String foodList(Model model) {
        List<Food> foods = foodService.getAllFoods();
        
        model.addAttribute("foods", foods);
        
        return "admin/food/foodList";
    }
    
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("foodDTO", new FoodDTO());
        return "admin/food/createFood";
    }
    
    @RequestMapping(value = "/processForm", method = {RequestMethod.GET, RequestMethod.POST })
    public String processForm(@Valid FoodDTO foodDTO, BindingResult bindingResult, Model model) {
        
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "admin/food/createFood";
        } else {
            // Validate image file
            MultipartFile imageFile = foodDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    if (!imageFile.getContentType().startsWith("image/")) {
                        bindingResult.rejectValue("imageFile", "error.imageFile", "Only image files are allowed!");
                        return "admin/food/createFood";
                    }
                    
                    // Check file extension
                    String fileName = imageFile.getOriginalFilename();
                    String fileExtension = StringUtils.getFilenameExtension(fileName);
                    List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png");
                    
                    if (!validExtensions.contains(fileExtension.toLowerCase()) ||
                        fileExtension.equalsIgnoreCase("exe") ||
                        fileExtension.equalsIgnoreCase("zip")) {
                        
                        bindingResult.rejectValue("imageFile", "error.imageFile", "Only image files with extensions jpg, jpeg, or png are allowed!");
                        return "admin/food/createFood";
                    }
                    
                    // Check file size
                    long fileSizeInBytes = imageFile.getSize();
                    long fileSizeInMB = fileSizeInBytes / (1024 * 1024); // Convert bytes to megabytes
                    if (fileSizeInMB >= 1) {
                        bindingResult.rejectValue("imageFile", "error.imageFile", "File size must be lower than 1MB!");
                        return "admin/food/createFood";
                    }
                } catch (Exception e) {
                    model.addAttribute("error", e.getMessage());
                    return "admin/food/createFood";
                }
                
            }
            
            // Save the food
            try {
                Food food = new Food();
                String imageUrl = UploadFileUtil.uploadFile(foodDTO.getImageFile());
                food.setImage(imageUrl);
                food.setFoodName(foodDTO.getFoodName());
                food.setSlug(foodDTO.getSlug());
                food.setDescription(foodDTO.getDescription());
                food.setPrice(foodDTO.getPrice());
                food.setTimeWait(food.getTimeWait());
                food.setId(foodDTO.getId());
                
                if (food.getId() != null) {
                    System.out.println("update");
                    foodService.updateFood(food);
                } else {
                    System.out.println("create");
                    foodService.saveFood(food);
                }
                
                return "redirect:/admin/food/list";
            } catch (Exception e) {
                model.addAttribute("foodExist", "Food already existed!");
                return "admin/food/createFood";
            }
        }
    }
    
    @GetMapping("/update")
    public String updateFood(@RequestParam("foodId") long foodId, Model model) {
        // get food from db
        Food food = foodService.getFoodById(foodId);
        
        // entity -> dto
        FoodDTO foodDTO = new FoodDTO();
        foodDTO.setId(foodId);
        foodDTO.setFoodName(food.getFoodName());
        foodDTO.setSlug(food.getSlug());
        foodDTO.setDescription(food.getDescription());
        foodDTO.setPrice(food.getPrice());
        foodDTO.setTimeWait(food.getTimeWait());
        
        // pass data to view
        model.addAttribute("foodDTO", foodDTO);
        model.addAttribute("prevImageUrl", food.getImage());
        
        return "admin/food/createFood";
    }
    
    @GetMapping("/delete")
    public String delete(@RequestParam("foodId") long foodId) {
        // get food from db
        Food food = foodService.getFoodById(foodId);
        UploadFileUtil.deleteFile(food.getImage());
        
        foodService.deleteFoodById((int) foodId);
        return "redirect:/admin/food/list";
    }
    
}
