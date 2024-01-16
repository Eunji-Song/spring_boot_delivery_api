package song.deliveryapi.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    // Common
    SUCCESS(200, HttpStatus.OK, "SUCCESS"),
    ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "ERROR"),
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "Server Error"),

    // Exception


    // @Valid
    INVALID_PARAMETER(400, HttpStatus.BAD_REQUEST, "올바르지 않은 값이 입력되었습니다."),

    // Auth
    INVALID_TOKEN(403, HttpStatus.FORBIDDEN, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN_TOKEN(403, HttpStatus.FORBIDDEN, "만료된 토큰입니다."),
    INVALID_USER(404, HttpStatus.NOT_FOUND, "존재하지 않는 사용자 정보 입니다."),


    // Controller - user
    DUPLICATED_USE(408, HttpStatus.CONFLICT, "이미 존재하는 회원의 정보입니다.");



    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    ResultCode(Integer code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
