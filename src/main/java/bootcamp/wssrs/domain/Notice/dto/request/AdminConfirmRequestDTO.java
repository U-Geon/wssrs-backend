package bootcamp.wssrs.domain.Notice.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AdminConfirmRequestDTO {
    @NotNull(message = "근무 지원 ID를 선택해주세요.")
    private List<Long> recruitIds;
}
