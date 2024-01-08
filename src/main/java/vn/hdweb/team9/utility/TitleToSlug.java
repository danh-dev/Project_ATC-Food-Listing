package vn.hdweb.team9.utility;

public class TitleToSlug {
    public static String toSlug(String title) {
        String slug = title.toLowerCase().trim();
        /*Convert utf8 to latin */
        slug = slug.replaceAll("[àáạảãâầấậẩẫăằắặẳẵ]", "a");
        slug = slug.replaceAll("[èéẹẻẽêềếệểễ]", "e");
        slug = slug.replaceAll("[ìíịỉĩ]", "i");
        slug = slug.replaceAll("[òóọỏõôồốộổỗơờớợởỡ]", "o");
        slug = slug.replaceAll("[ùúụủũưừứựửữ]", "u");
        slug = slug.replaceAll("[ỳýỵỷỹ]", "y");
        slug = slug.replaceAll("đ", "d");
        /*Remove special characters*/
        slug = slug.replaceAll("([^0-9a-z-\\s])", "");
        /*Remove spaces*/
        slug = slug.replaceAll("[\\s]", "-");
        return slug;
    }
}
