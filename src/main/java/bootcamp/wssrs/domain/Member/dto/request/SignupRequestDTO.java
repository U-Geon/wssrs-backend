package bootcamp.wssrs.domain.Member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record SignupRequestDTO(
        @NotBlank(message = "학번을 입력해주세요.")
        @Pattern(regexp = "^\\d{8}$", message = "8자리의 숫자만 입력해주세요.")
        String studentId,

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[~!@#$%^&*()_+`\\-={}|:\";'<>?,./]).{8,}$", message = "영문자, 숫자, 특수문자를 각각 최소 1개 이상 사용해주세요.")
        @NotBlank(message = "비밀번호를 입력해주세요.")
        String password,

        @Pattern(regexp = "^[a-zA-Z가-힣]+$", message = "올바르지 않은 형식입니다.")
        @NotBlank(message = "이름을 입력해주세요.")
        String username,

        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바르지 않은 이메일 형식입니다.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email
) {}
