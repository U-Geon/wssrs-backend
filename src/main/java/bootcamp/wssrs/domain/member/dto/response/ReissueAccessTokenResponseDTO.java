package bootcamp.wssrs.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ReissueAccessTokenResponseDTO {
    private String newAccessToken;
}
