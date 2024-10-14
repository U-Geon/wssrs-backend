package bootcamp.wssrs.domain.Notice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.List;

public record UserRecruitRequestDTO(
        @NotBlank(message = "근무 코드를 입력해주세요.")
        String code,

        @Pattern(regexp = "^(01[016789]-?\\d{3,4}-?\\d{4}|\\d{2,3}-?\\d{3,4}-?\\d{4})$")
        @NotBlank(message = "전화번호를 입력해주세요.")
        String phoneNum,

        @NotEmpty(message = "근무 날짜를 한 개 이상 선택해주세요.")
        List<String> day,

        @NotNull(message = "조합원 가입 유무에 체크해주세요.")
        Boolean isUnion
) {}
