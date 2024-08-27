package bootcamp.wssrs.domain.Notice.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserRecruitRequestDTO {
    @NotBlank(message = "근무 코드를 입력해주세요.")
    private String code;

    @Pattern(regexp = "^(01[016789]-?\\d{3,4}-?\\d{4}|\\d{2,3}-?\\d{3,4}-?\\d{4})$")
    @NotBlank(message = "전화번호를 입력해주세요.")
    private String phoneNum;

    @NotBlank(message = "근무 날짜를 한 개 이상 선택해주세요.")
    private List<String> day;

    @NotBlank(message = "조합원 가입 유무에 체크해주세요.")
    private Boolean isUnion;
}
