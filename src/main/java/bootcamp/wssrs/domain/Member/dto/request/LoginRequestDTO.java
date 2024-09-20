package bootcamp.wssrs.domain.Member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record LoginRequestDTO(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바르지 않은 이메일 형식입니다.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+`\\-={}|:\";'<>?,./]).{8,}$", message = "영문자, 숫자, 특수문자를 각각 최소 1개 이상 사용해주세요.")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password
) {}
