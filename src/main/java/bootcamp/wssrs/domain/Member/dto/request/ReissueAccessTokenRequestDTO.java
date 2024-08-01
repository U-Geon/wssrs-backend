package bootcamp.wssrs.domain.Member.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReissueAccessTokenRequestDTO {
    private String refreshToken;
}
