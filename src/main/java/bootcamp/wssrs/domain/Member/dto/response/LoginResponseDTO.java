package bootcamp.wssrs.domain.Member.dto.response;

public record LoginResponseDTO(
        String accessToken,
        String refreshToken,
        String username,
        String studentId
) {}
