package song.deliveryapi.common.exception;


import org.springframework.web.bind.annotation.ExceptionHandler;

public class ApplicationException extends RuntimeException {
    private Boolean success;
    private int code;
    private String message;

    public ApplicationException(Boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}