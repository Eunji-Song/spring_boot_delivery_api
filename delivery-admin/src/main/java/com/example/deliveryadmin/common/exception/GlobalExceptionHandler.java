package com.example.deliveryadmin.common.exception;

import com.example.deliveryadmin.common.response.ApiResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public ResponseEntity<Object> buildApiResult(HttpStatusCode statusCode, String detail,HttpHeaders headers ) {
        ApiResult result = new ApiResult(statusCode.value(), detail);
        ResponseEntity.badRequest();
        return new ResponseEntity<Object>(result, headers, statusCode);
    }


    // 모든 예외 처리
    @ExceptionHandler(ApplicationException.class)
    public final ResponseEntity<Object> handleApplicationException(ApplicationException ex) {
        log.error("ApplicationException caught: {}", ex.getMessage());

        HttpStatus status = ex.getResultCode().getHttpStatus();
        return buildApiResult(status, ex.getMessage(), new HttpHeaders());
    }

    // HTTP 메서드 오류
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("HttpRequestMethodNotSupportedException caught: {}", ex.getMessage());

        return buildApiResult(status, ex.getBody().getDetail(), headers);
    }

    // @Valid 오류 
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("MethodArgumentNotValidException caught: {}", ex.getMessage());

        return buildApiResult(status, ex.getBody().getDetail(), headers);
    }


    // 403
    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest request) {
        log.error("Authentication error: {}", ex.getMessage());

        return buildApiResult(HttpStatus.FORBIDDEN, ex.getMessage(), new HttpHeaders());
    }


}
