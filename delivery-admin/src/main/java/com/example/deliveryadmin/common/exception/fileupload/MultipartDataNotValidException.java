package com.example.deliveryadmin.common.exception.fileupload;

public class MultipartDataNotValidException extends RuntimeException {

    public MultipartDataNotValidException(String message) {
        super(message);
    }

    public MultipartDataNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
