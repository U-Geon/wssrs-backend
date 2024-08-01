package bootcamp.wssrs.global.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;

// Custom Error 내용을 담을 Response Entity
@Data
@Builder
public class ErrorResponseEntity {
    private int status;
    private String code;
    private String message;

    public static ResponseEntity<ErrorResponseEntity> toResponseEntity(ErrorCode e){
        return ResponseEntity
                .status(e.getHttpStatus())
                .body(ErrorResponseEntity.builder()
                        .status(e.getHttpStatus().value())
                        .code(e.name())
                        .message(e.getMessage())
                        .build()
                );
    }
}