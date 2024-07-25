package bootcamp.wssrs.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;

    public TokenDTO(String accessToken) {
        this.accessToken = accessToken;
        this.refreshToken = null;
    }
}
