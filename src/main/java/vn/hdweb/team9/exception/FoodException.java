package vn.hdweb.team9.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FoodException extends RuntimeException {
    private int code;
    private String message;
    
    public FoodException(String message, int code, String message1) {
        super(message);
        this.code = code;
        this.message = message1;
    }
    
    public FoodException(String message, Throwable cause, int code, String message1) {
        super(message, cause);
        this.code = code;
        this.message = message1;
    }
    
    public FoodException(Throwable cause, int code, String message) {
        super(cause);
        this.code = code;
        this.message = message;
    }
    
    public FoodException(String message,
                         Throwable cause,
                         boolean enableSuppression,
                         boolean writableStackTrace,
                         int code,
                         String message1
                        ) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.message = message1;
    }
}
