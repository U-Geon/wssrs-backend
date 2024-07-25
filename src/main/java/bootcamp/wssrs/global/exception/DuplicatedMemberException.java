package bootcamp.wssrs.global.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicatedMemberException extends DataIntegrityViolationException {
    public DuplicatedMemberException(String msg) {
        super(msg);
    }

    public DuplicatedMemberException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
