package com.example.deliveryuser.common.response;

import org.springframework.validation.BindingResult;

/**
 * 제네릭 타입을 이용하여 받는 값을 유연하게 처리
 */
public class ApiResponse {
    static final ResultCode SUCCESS = ResultCode.SUCCESS;
    static final ResultCode ERROR = ResultCode.ERROR;
    static final ResultCode INVALID_PARAMETER = ResultCode.INVALID_PARAMETER;



    // == Success == //

    /**
     * 매개변수를 받지 않는 메서드
     */
    public static ApiResult success() {
        return new ApiResult(SUCCESS);
    }

    /**
     * ResultCode를 매개변수로 받는 메서드
     * 특정 Code에 대한 Response return
     */
    public static ApiResult success(ResultCode resultCode) {
        return new ApiResult<>(resultCode);
    }

    /**
     * Resultcode, Data를 매개변수로 받는 메서드
     */
    public static <T> ApiResult<T> success(ResultCode resultCode, T data) {
        return new ApiResult<>(resultCode, data);
    }

    /**
     * 성공 리턴시 메시지 내용을 커스텀으로 작성
     */
    public static ApiResult success(String message) {
        return new ApiResult(SUCCESS, message);
    }


    // == Error == //
    /**
     * 매개변수를 받지 않는 메서드
     */
    public static ApiResult error() {
        return new ApiResult(ERROR);
    }

    /**
     * ResultCode를 매개변수로 받는 메서드
     * 특정 Code에 대한 Response return
     */
    public static ApiResult error(ResultCode resultCode) {
        return new ApiResult<>(resultCode);
    }

    public static ApiResult error(int httpStatusCode, String message) {
        return new ApiResult<>(httpStatusCode, message);
    }

    /**
     * Resultcode, Data를 매개변수로 받는 메서드
     */
    public static <T> ApiResult<T> error(ResultCode resultCode, T data) {
        return new ApiResult<>(resultCode, data);
    }

    /**
     * 성공 리턴시 메시지 내용을 커스텀으로 작성
     */
    public static ApiResult error(String message) {
        return new ApiResult(ERROR, message);
    }


    // == Valid err == //

    /**
     * @Valid 검증 오류 발생 시 첫번째 에러 메시지 리턴
     */
    public static ApiResult validErrorSingleRow(BindingResult bindingResult) {
        String errmsg = bindingResult.getAllErrors().get(0).getDefaultMessage();
        return new ApiResult(ERROR);
    }



}
