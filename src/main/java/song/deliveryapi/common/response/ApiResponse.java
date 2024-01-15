package song.deliveryapi.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.*;

/**
 * API 공통 응답 포맷 생성
 * 오버로딩을 이용하여 같은 메서드명으로 파라미터만 다르게 할당 받도록 구현
 */

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class ApiResponse<T> {
    // == Return 필드 == //
    private Integer code; // ResultCode.code
    private String message; // Resultcode.message
    // null값인 경우 필드 미노출
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data; // 리턴 데이터

    // == Response Result Code === //
    public ApiResponse(ResultCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }

    public ApiResponse(ResultCode resultCode, String message, T data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public ApiResponse(ResultCode resultCode, String message) {
        this.code = resultCode.getCode();
        this.message = message;
    }


    // == Response Custom == //
    public ApiResponse(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }




    // == success == //
    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ResultCode.SUCCEES);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ResultCode.SUCCEES, "message", data);
    }

    public static <T> ApiResponse<T> success(Integer code, String message) {
        return new ApiResponse<>(code, message);
    }

    public static <T> ApiResponse<T> success(Integer code, String message, T data) {
        return new ApiResponse<>(code, message, data);
    }

    // == error == //
    public static <T> ApiResponse<T> error() {
        return new ApiResponse<>(ResultCode.ERROR);
    }




    // == binding result  == //
    public static <T> ApiResponse<T> validError(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();

        List<ObjectError> allErrors = bindingResult.getAllErrors();
        for (ObjectError error : allErrors) {
            if (error instanceof FieldError) {
                errors.put(((FieldError) error).getField(), error.getDefaultMessage());
            } else {
                errors.put( error.getObjectName(), error.getDefaultMessage());
            }
        }
        return new ApiResponse(ResultCode.VALIDERR, errors);
    }

    public static <T> ApiResponse<T> validErrorSingle(BindingResult bindingResult) {
        String firstMessage = bindingResult.getFieldErrors().get(0).getDefaultMessage();
        return new ApiResponse(ResultCode.VALIDERR, firstMessage);
    }

    private ApiResponse(ResultCode code, T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }
}