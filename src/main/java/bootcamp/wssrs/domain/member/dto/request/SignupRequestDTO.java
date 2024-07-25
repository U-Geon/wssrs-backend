package bootcamp.wssrs.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SignupRequestDTO {
    private String studentId;
    private String password;
    private String username;
    private String email;
}
