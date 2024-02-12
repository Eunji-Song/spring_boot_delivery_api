package com.example.deliveryuser.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    /* COMMON - 모든 페이지에서 사용*/
    SUCCESS(HttpStatus.OK, 200, "SUCCESS"),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "ERROR"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),

    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "데이터를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "Method를 확인해주세요."),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, 400, "올바르지 않은 값이 입력되었습니다."),
    CONFLICT(HttpStatus.CONFLICT, 409, "충동이 발생하였습니다. "),

    // Auth
    UNAUTHORIZED_USER(HttpStatus.UNAUTHORIZED, 401, "인증되지 않은 사용자입니다."), // 익명상태
    EXPIRED_TOKEN(HttpStatus.FORBIDDEN, 403, "유효하지 않은 토큰입니다."), // 토큰 만료 or 유효하지 않은ㅇ 토큰
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "실행권한이 없습니다."),
    INVALID_USER(HttpStatus.NOT_FOUND, 404, "존재하지 않는 사용자 정보입니다."),

    // Member
    JOIN_SUCCESS(HttpStatus.OK, 200, "회원가입이 완료되었습니다."),
    DATA_DUPLICATION_USER(HttpStatus.CONFLICT, 409, "이미 존재하는 회원입니다."),

    // 파일 업로드 관련
    FILE_SIZE_EXCEEDED(HttpStatus.PAYLOAD_TOO_LARGE, 413, "파일 크기가 허용한 한도를 초과했습니다."),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, 415, "지원되지 않는 미디어 타입입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 내부 오류가 발생했습니다."),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, 503, "현재 서비스 이용이 불가능한 상태입니다."),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, 422, "처리할 수 없는 엔터티입니다."),



    // store

    // product
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "매장을 찾을 수 없습니다. ");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ResultCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
