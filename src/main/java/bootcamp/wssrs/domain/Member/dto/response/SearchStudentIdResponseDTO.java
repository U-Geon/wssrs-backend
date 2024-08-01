package bootcamp.wssrs.domain.Member.dto.response;

import bootcamp.wssrs.domain.Member.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SearchStudentIdResponseDTO {
    private String msg;
    private String password;

    public SearchStudentIdResponseDTO(Member member) {
        this.msg = "ok";
        this.password = member.getPassword();
    }
}
