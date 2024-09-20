package bootcamp.wssrs.domain.Member.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public record ReissueAccessTokenRequestDTO(
        @NotBlank(message = "토큰이 존재하지 않습니다.")
        String refreshToken
) {}