package vn.hdweb.team9.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    
    @GetMapping("/list")
    public String categoryList() {
        return "admin/category/categoryList";
    }
    
    @GetMapping("/new")
    public String createCategory() {
        return "admin/category/createCategory";
    }
    
    @GetMapping("/upload")
    public String getUpload() {
        return "uploadFile";
    }
    
    @PostMapping(value = "/upload")
    public String upLoad(Model model, @RequestParam("image") MultipartFile file) {
        String filePath = UploadController.uploadImage(model, file);
        if (filePath != null) {
            System.out.println(filePath);
        }
        return "uploadFile";
    }
}
