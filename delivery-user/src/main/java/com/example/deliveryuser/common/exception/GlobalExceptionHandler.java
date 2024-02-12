package com.example.deliveryuser.common.exception;

import com.example.deliveryuser.common.exception.auth.InvalidTokenException;
import com.example.deliveryuser.common.response.ApiResult;
import com.example.deliveryuser.common.response.ResultCode;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;



@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private ResultCode resultCode;
    // 모든 예외 처리
    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<Object> handleApplicationException(ApplicationException ex, HttpHeaders headers) {
        log.error("ApplicationException caught: {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(resultCode.BAD_REQUEST);
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

    @ExceptionHandler({RuntimeException.class, InvalidTokenException.class})
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        log.info("handleRuntimeException : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(resultCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }


    // HTTP 메서드 오류
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpRequestMethodNotSupportedException caught: {}", ex.getMessage());
        ApiResult apiResult = new ApiResult(resultCode.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

    // @Valid 오류
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("handleMethodArgumentNotValid caught: {}", ex.getMessage());

        // 오류 내용 추출
        String targetField = ex.getFieldError().getField();
        String errMsg = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

        // return
        ApiResult apiResult = new ApiResult(resultCode.METHOD_NOT_ALLOWED, errMsg);
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

    // 400
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("handleTypeMismatch caught: {}", ex.getMessage());

        String message = "파라미터 값을 확인해주세요.";
        if (ex.getPropertyName() != null) {
            message = ex.getPropertyName()  +  " 파라미터 값을 확인해주세요.";
        }


        ApiResult apiResult = new ApiResult(resultCode.BAD_REQUEST, message);
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

     // 403
    @ExceptionHandler({AuthenticationException.class})
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication error: {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(resultCode.UNAUTHORIZED_USER, "존재하지 않는 사용자의 정보가 입력되었습니다.");
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    // 404
    @ExceptionHandler(value = {NotFoundException.class, UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest webRequest) {
        log.error("handleNotFoundException error : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(resultCode.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }


    // 409
    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex) {
        log.error("handleConflictException error : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(resultCode.CONFLICT, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("handleHttpMessageNotReadable : {}", ex.getMessage());

        String message = ex.getMessage();
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            message = invalidFormatException.getPath().get(0).getFieldName() + " 필드 값을 확인해주세요. ";
        }


        ApiResult apiResult = new ApiResult(resultCode.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    // IllegalArgumentException
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex) {
        log.error("handleIllegalArgumentException : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(resultCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception ex) {
        log.error("handleException : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(resultCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }



}
