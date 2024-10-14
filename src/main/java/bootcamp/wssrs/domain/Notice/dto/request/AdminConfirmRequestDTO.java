package bootcamp.wssrs.domain.Notice.dto.request;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AdminConfirmRequestDTO(
        @NotNull(message = "근무 지원 ID를 선택해주세요.")
        List<Long> recruitIds
) {}
