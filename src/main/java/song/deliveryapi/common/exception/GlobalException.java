package song.deliveryapi.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import song.deliveryapi.common.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
@Slf4j
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ApiResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        log.info("errors {}", errors);
        return ApiResponse.validError(e.getMessage());
    }

    @ExceptionHandler(ApplicationException.class)
    protected ApiResponse ApplicationExceptionHandler(ApplicationException e) {
        return ApiResponse.error(e.getCode(), e.getMessage());
    }
}
