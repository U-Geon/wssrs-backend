package bootcamp.wssrs.domain.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordUpdateResponseDTO {

    private String msg;
    private String newPassword;

    public PasswordUpdateResponseDTO(String newPassword) {
        this.msg = "ok";
        this.newPassword = newPassword;
    }
}
