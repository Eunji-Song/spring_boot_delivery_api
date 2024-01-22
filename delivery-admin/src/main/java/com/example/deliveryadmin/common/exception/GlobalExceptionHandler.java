package com.example.deliveryadmin.common.exception;

import com.example.deliveryadmin.common.response.ApiResult;
import com.example.deliveryadmin.common.response.ResultCode;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // 모든 예외 처리
    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<Object> handleApplicationException(ApplicationException ex, HttpHeaders headers) {
        log.error("ApplicationException caught: {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(ResultCode.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        log.info("handleRuntimeException : {}", ex.getMessage());
        ApiResult apiResult = new ApiResult(ResultCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }


    // HTTP 메서드 오류
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpRequestMethodNotSupportedException caught: {}", ex.getMessage());
        ApiResult apiResult = new ApiResult(ResultCode.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

    // @Valid 오류
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("MethodArgumentNotValidException caught: {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(ResultCode.METHOD_NOT_ALLOWED, ex.getMessage());
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

    // 400
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("handleTypeMismatch caught: {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(ResultCode.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(apiResult, headers, HttpStatus.OK);
    }

     // 403
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        log.error("Authentication error: {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(ResultCode.UNAUTHORIZED_USER, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    // 404
    @ExceptionHandler(value = {NotFoundException.class, UsernameNotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest webRequest) {
        log.error("handleNotFoundException error : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(ResultCode.NOT_FOUND, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    // 409
    @ExceptionHandler(ConflictException.class)
    protected ResponseEntity<Object> handleConflictException(ConflictException ex) {
        log.error("handleConflictException error : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(ex.getResultCode(), ex.getResultCode().getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("handleHttpMessageNotReadable : {}", ex.getMessage());

        String message = ex.getMessage();
        if (ex.getCause() instanceof InvalidFormatException invalidFormatException) {
            message = invalidFormatException.getPath().get(0).getFieldName() + " 필드 값을 확인해주세요. ";
        }


        ApiResult apiResult = new ApiResult(ResultCode.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }

    // IllegalArgumentException
    @ExceptionHandler(value = {IllegalArgumentException.class})
    protected ResponseEntity<Object> handleIllegalArgumentException(RuntimeException ex) {
        log.error("handleIllegalArgumentException : {}", ex.getMessage());

        ApiResult apiResult = new ApiResult(ResultCode.BAD_REQUEST, ex.getMessage());
        return ResponseEntity.status(HttpStatus.OK).body(apiResult);
    }


}
