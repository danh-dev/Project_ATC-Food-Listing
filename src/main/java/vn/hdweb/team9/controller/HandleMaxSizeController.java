package vn.hdweb.team9.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.DateTimeException;

@ControllerAdvice
public class HandleMaxSizeController {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public RedirectView handleMaxSizeException(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error_upload", "Kích thước ảnh không được vượt quá 5MB.");
        String referer = request.getHeader("Referer");
        return new RedirectView(referer);
    }
    @ExceptionHandler(RuntimeException.class)
    public RedirectView handleRuntimeException(RuntimeException ex, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("RuntimeError", ex.getMessage());
        String referer = request.getHeader("Referer");
        return new RedirectView(referer);
    }
    @ExceptionHandler(DateTimeException.class)
    public RedirectView handleCoupon(RedirectAttributes redirectAttributes, HttpServletRequest request) {
        redirectAttributes.addFlashAttribute("error_coupon", "Coupon hết hạn.");
        String referer = request.getHeader("Referer");
        return new RedirectView(referer);
    }
}
