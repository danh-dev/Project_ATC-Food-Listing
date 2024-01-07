package vn.hdweb.team9.utility;

public class StringToSlugUtil {
    
    public static String toSlug(String title) {
        // Đổi chữ hoa thành chữ thường
        String slug = title.toLowerCase();
        
        // Đổi ký tự có dấu thành không dấu
        slug = slug.replaceAll("[áàảạãăắằẳẵặâấầẩẫậ]", "a");
        slug = slug.replaceAll("[éèẻẽẹêếềểễệ]", "e");
        slug = slug.replaceAll("[íìỉĩị]", "i");
        slug = slug.replaceAll("[óòỏõọôốồổỗộơớờởỡợ]", "o");
        slug = slug.replaceAll("[úùủũụưứừửữự]", "u");
        slug = slug.replaceAll("[ýỳỷỹỵ]", "y");
        slug = slug.replaceAll("đ", "d");
        
        // Xóa các ký tự đặc biệt
        slug = slug.replaceAll("[^a-z0-9\\s-]", "");
        
        // Đổi khoảng trắng thành ký tự gạch ngang
        slug = slug.replaceAll(" ", "-");
        
        // Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
        slug = slug.replaceAll("-{2,}", "-");
        
        // Xóa các ký tự gạch ngang ở đầu và cuối
        slug = slug.replaceAll("^-|-$", "");
        
        return slug;
    }
}
