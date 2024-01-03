package vn.hdweb.team9.controller.admin;

import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class UploadController {
    public static String UPLOAD_DIRECTORY = System.getProperty("user.dir") + "/uploads";
    
    // if success return path of saved image, else return null
    // The model added attribute named error that used for validating.
    public static String uploadImage(Model model, MultipartFile file) {
        if (file.getOriginalFilename() != null && !file.isEmpty()) {
            // check file size
            long fileSizeInBytes = file.getSize();
            long fileSizeInMB = fileSizeInBytes / (1024 * 1024); // Convert bytes to megabytes
            if (fileSizeInMB >= 1) {
                model.addAttribute("errorUpload", "File size must be lower 1MB!");
            }
            
            // Check Img Type
            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                model.addAttribute("errorUpload", "Only image files are allowed!");
                return null;
            }
            
            // Check extensions type
            String fileName = file.getOriginalFilename();
            assert fileName != null;
            String fileExtension = StringUtils.getFilenameExtension(fileName);
            List<String> validExtensions = Arrays.asList("jpg", "jpeg", "png");
            
            if (!validExtensions.contains(fileExtension.toLowerCase())) {
                model.addAttribute("errorUpload", "Only image files are allowed!");
                return null;
            }
            
            // Check file type
            if (fileExtension.equalsIgnoreCase("exe") || fileExtension.equalsIgnoreCase("zip")) {
                model.addAttribute("errorUpload", "File type not allowed!");
                return null;
            }
            
            Path fileNameAndPath = null;
            
            try {
                String uploadDir = UPLOAD_DIRECTORY + "/" + LocalDateTime.now().getYear() + "/" +
                                   LocalDateTime.now().getMonthValue() + "/";
                String filename = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + "_" + file.getOriginalFilename();
                
                fileNameAndPath = Paths.get(uploadDir, filename);
                
                Path path = Paths.get(uploadDir);
                
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                
                Files.write(fileNameAndPath, file.getBytes());
                
                model.addAttribute("successUpload", "Uploaded image: " + file.getOriginalFilename());
                
            } catch (IOException e) {
                model.addAttribute("errorUpload", e.getMessage());
            }
            return fileNameAndPath.toString();
        } else {
            model.addAttribute("errorUpload", "File must be not empty!");
            return null;
        }
        
    }
    
    public static boolean deleteFile(String avatar, Model model) {
        try {
            Path path = Paths.get(avatar);
            Files.delete(path);
            return true;
        } catch (Exception e) {
            model.addAttribute("errorUpload", e.getMessage());
            return false;
        }
    }
    
}

