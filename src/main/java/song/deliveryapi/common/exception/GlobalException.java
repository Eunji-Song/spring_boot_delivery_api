package song.deliveryapi.common.exception;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
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

        // Error 할당
        BindingResult bindingResult = e.getBindingResult();
        int errCount = bindingResult.getErrorCount();
        String defaultErrMessage = bindingResult.getFieldError().getDefaultMessage();

        // if (errCount > )

        log.info("size : {}" , e.getBindingResult().getFieldErrors().size());

        e.getBindingResult().getFieldErrors().forEach(error -> {
            log.info("err {}", error.getDefaultMessage());
            errors.put(error.getField(), error.getDefaultMessage());
        });

        log.info("dd {}", bindingResult.getFieldError().getDefaultMessage());






        log.info("errors {}", errors);
        return ApiResponse.validError(defaultErrMessage);
    }

    @ExceptionHandler(ApplicationException.class)
    protected ApiResponse ApplicationExceptionHandler(ApplicationException e) {
        return ApiResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ApiResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.info("info {}", e.getCause());
        log.info("reason1 {}", e.getMessage());
        log.info("reason2 {}", e.getHttpInputMessage());

        if (e.getCause() instanceof MismatchedInputException mismatchedInputException) {
            log.info("err");
        }
        return ApiResponse.error();
    }


}
