package bootcamp.wssrs.global.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class MemberNotFoundError extends BadCredentialsException {
    public MemberNotFoundError(String message) {
        super(message);
    }
}
