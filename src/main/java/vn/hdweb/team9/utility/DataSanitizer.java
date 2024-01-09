package vn.hdweb.team9.utility;

public class DataSanitizer {

    public static String clear(String input) {
        if (input == null) {
            return null;
        }
        input = input.trim();
        input = input.replaceAll("\\s+", " ");
        return input;
    }
}
