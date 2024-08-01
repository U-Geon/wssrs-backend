package bootcamp.wssrs.domain.Member.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class PasswordUpdateResponseDTO {

    private String msg;
    private String newPassword;

    public PasswordUpdateResponseDTO(String newPassword) {
        this.msg = "ok";
        this.newPassword = newPassword;
    }
}
