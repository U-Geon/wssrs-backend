package bootcamp.wssrs.domain.member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordUpdateRequestDTO {
    private String newPassword;
}
