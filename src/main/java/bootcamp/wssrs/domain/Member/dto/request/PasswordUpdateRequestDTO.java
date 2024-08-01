package bootcamp.wssrs.domain.Member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PasswordUpdateRequestDTO {

    @NotNull
    private String newPassword;
}
