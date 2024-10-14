package bootcamp.wssrs.domain.Member.dto.response;

public record PasswordUpdateResponseDTO(
        String msg,
        String newPassword
) {
    public PasswordUpdateResponseDTO(String newPassword) {
        this("ok", newPassword);
    }
}
