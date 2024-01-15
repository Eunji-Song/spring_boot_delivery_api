package song.deliveryapi.common.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResultCode {
    SUCCEES(200, HttpStatus.OK, "SUCCESS"),
    ERROR(401, HttpStatus.BAD_REQUEST, "ERROR"),
    VALIDERR(401, HttpStatus.BAD_REQUEST, "Valid Error");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;

    ResultCode(Integer code, HttpStatus httpStatus, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.message = message;
    }


}
