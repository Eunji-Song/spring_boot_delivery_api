package com.example.deliveryuser.common.exception.fileupload;

public class InvalidFileException extends RuntimeException{

    private final String INVALID_FILE_ERROR = "잘못된 형식의 파일입니다.";
    public InvalidFileException(String message) {
        super(message);
    }

    public InvalidFileException() {

    }
}
