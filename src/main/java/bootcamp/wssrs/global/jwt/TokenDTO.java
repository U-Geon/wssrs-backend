package bootcamp.wssrs.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor // JSON -> JAVA로 역직렬화를 위한 기본 생성자 삽입.
@AllArgsConstructor
public class TokenDTO {
    private String accessToken;
    private String refreshToken;

    public TokenDTO(String accessToken) {
        this.accessToken = accessToken;
        this.refreshToken = null;
    }
}
