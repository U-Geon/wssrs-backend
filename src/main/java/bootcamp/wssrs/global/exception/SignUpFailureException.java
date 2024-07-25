package bootcamp.wssrs.global.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class SignUpFailureException extends BadCredentialsException {
    public SignUpFailureException(String msg) {
        super(msg);
    }

    public SignUpFailureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
