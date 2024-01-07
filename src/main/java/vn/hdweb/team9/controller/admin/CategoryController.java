package vn.hdweb.team9.controller.admin;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import vn.hdweb.team9.domain.dto.CategoryDTO;
import vn.hdweb.team9.domain.entity.Category;
import vn.hdweb.team9.service.CategoryService;
import vn.hdweb.team9.utility.UploadFileUtil;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    
    private CategoryService categoryService;
    
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    
    @GetMapping("/list")
    public String categoryList(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        
        for(Category category : categories) {
            String displayUrl =  category.getImage();
            displayUrl = displayUrl.replace('\\', '/');
            category.setImage(displayUrl);
        }
        
        model.addAttribute("categories", categories);
        
        return "admin/category/categoryList";
    }
    
    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("categoryDTO", new CategoryDTO());
        return "admin/category/createCategory";
    }
    
    @PostMapping("/processForm")
    public String processForm(@Valid CategoryDTO categoryDTO, BindingResult bindingResult, Model model) {
        
        // Check for validation errors
        if (bindingResult.hasErrors()) {
            return "admin/category/createCategory";
        } else {
            // Validate image file
            MultipartFile imageFile = categoryDTO.getImageFile();
            if (imageFile != null && !imageFile.isEmpty()) {
                if (!imageFile.getContentType().startsWith("image/")) {
                    bindingResult.rejectValue("imageFile", "error.imageFile", "Only image files are allowed!");
                    return "admin/category/createCategory";
                }
                
                // Check file extension
                String fileName = imageFile.getOriginalFilename();
                String fileExtension = StringUtils.getFilenameExtension(fileName);
                List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png");
                
                if (!validExtensions.contains(fileExtension.toLowerCase()) ||
                    fileExtension.equalsIgnoreCase("exe") ||
                    fileExtension.equalsIgnoreCase("zip")) {
                    
                    bindingResult.rejectValue("imageFile", "error.imageFile", "Only image files with extensions jpg, jpeg, or png are allowed!");
                    return "admin/category/createCategory";
                }
                
                // Check file size
                long fileSizeInBytes = imageFile.getSize();
                long fileSizeInMB = fileSizeInBytes / (1024 * 1024); // Convert bytes to megabytes
                if (fileSizeInMB >= 1) {
                    bindingResult.rejectValue("imageFile", "error.imageFile", "File size must be lower than 1MB!");
                    return "admin/category/createCategory";
                }
                
            }
            
            // Save the category
            try {
                Category category = new Category();
                String imageUrl = UploadFileUtil.uploadFile(categoryDTO.getImageFile());
                category.setImage(imageUrl);
                category.setCategoryName(categoryDTO.getCategoryName());
                category.setId(categoryDTO.getId());
                
                if (category.getId() != null) {
                    System.out.println("update");
                    categoryService.updateCategory(category);
                } else {
                    System.out.println("create");
                    categoryService.saveCategory(category);
                }
                
                return "redirect:/admin/category/list";
            } catch (Exception e) {
                model.addAttribute("categoryExist", "Category already existed!");
                return "admin/category/createCategory";
            }
        }
    }
    
    @GetMapping("/update")
    public String updateCategory(@RequestParam("categoryId") long categoryId, Model model) {
        // get category from db
        Category category = categoryService.getCategoryById(categoryId);
        
        // entity -> dto
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryId);
        categoryDTO.setCategoryName(category.getCategoryName());
        
        // pass data to view
        model.addAttribute("categoryDTO", categoryDTO);
        model.addAttribute("prevImageUrl", category.getImage());
        
        return "admin/category/createCategory";
    }
    
    @GetMapping("/delete")
    public String delete(@RequestParam("categoryId") long categoryId) {
        // get category from db
        Category category = categoryService.getCategoryById(categoryId);
        UploadFileUtil.deleteFile(category.getImage());
        
        categoryService.deleteCategoryById((int) categoryId);
        return "redirect:/admin/category/list";
    }
    
}
