package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class SeatUnavailableException extends RuntimeException{
    public SeatUnavailableException(String cause) {
        super(cause);
    }
}
