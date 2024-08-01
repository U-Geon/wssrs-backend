package bootcamp.wssrs.domain.Notice.dto.request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class UserRecruitRequestDTO {
    private String code;
    private String phoneNum;
    private List<String> day;
    private Boolean isUnion;
}
