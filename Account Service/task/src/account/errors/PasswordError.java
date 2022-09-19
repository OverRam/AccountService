package account.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class PasswordError extends RuntimeException {
    public PasswordError(String message) {
        super(message);
    }
}
