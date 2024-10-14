package bootcamp.wssrs.domain.Member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record FindMemberRequestDTO(
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "올바르지 않은 이메일 형식입니다.")
        @NotBlank(message = "이메일을 입력해주세요.")
        String email,

        @NotBlank(message = "학번을 입력해주세요.")
        @Pattern(regexp = "^\\d{8}$", message = "올바르지 않은 형식입니다.")
        String studentId
) {}
