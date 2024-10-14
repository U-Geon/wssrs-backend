package bootcamp.wssrs.domain.Member.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ReissueAccessTokenRequestDTO(
        @NotBlank(message = "토큰이 존재하지 않습니다.")
        String refreshToken
) {}