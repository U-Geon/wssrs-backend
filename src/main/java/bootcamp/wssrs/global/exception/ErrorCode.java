package bootcamp.wssrs.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 에러 코드
@Getter
@AllArgsConstructor
public enum ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH-001", "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH-002", "비밀번호가 일치하지 않습니다."),
    DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "AUTH-003", "이미 가입된 계정입니다."),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "AUTH-004", "누락된 정보가 있는지 확인해주세요."),

    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE-001", "게시글을 찾을 수 없습니다."),

    ;
    // ...

    private final HttpStatus httpStatus;	// HttpStatus
    private final String code;				// ACCOUNT-001
    private final String message;			// 설명
    }