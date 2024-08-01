package bootcamp.wssrs.domain.Notice.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class AdminConfirmRequestDTO {
    private List<Long> recruitIds;
}
