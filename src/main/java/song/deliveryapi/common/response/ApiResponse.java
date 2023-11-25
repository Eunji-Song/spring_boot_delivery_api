package song.deliveryapi.common.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 성공 건에 대한 응답 형식
 */

@Getter
public class ApiResponse<T> {
    private int code;
    private String message;
    private T data;

    @Builder
    public ApiResponse(int code, String message, T data) {
        this.code = code; // 내부 에러 코드
        this.message = message; // 에러 메시지 or 성공 메시지
        this.data = data;
    }

    public static ApiResponse success(){
        return ApiResponse.builder()
                .code(200)
                .message("SUCCESS")
                .build();
    }

    public static <T> ApiResponse<T> success(T data) {
        ApiResponse response = ApiResponse.builder().code(200).message("SUCCESS").data(data).build();
        return response;
    }

    public static ApiResponse error() {
        return ApiResponse.builder()
                .code(-1)
                .message("ERROR")
                .build();
    }

    public static ApiResponse error(int code, String message){
        return ApiResponse.builder()
                .code(code)
                .message(message)
                .build();
    }

    public static ApiResponse validError(String message) {
        return ApiResponse.builder()
                .code(404)
                .message(message)
                .build();
    }

}
