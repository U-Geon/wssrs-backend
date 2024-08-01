package bootcamp.wssrs.domain.Member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String refreshToken;
    private String username;
    private String studentId;
}
