package song.deliveryapi.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import song.deliveryapi.common.response.ResultCode;

// unchecked Exception
@Getter
public class ApplicationException extends RuntimeException {
    private final ResultCode resultCode;

    public ApplicationException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
