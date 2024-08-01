package bootcamp.wssrs.domain.Notice.dto.response;

import bootcamp.wssrs.domain.Notice.entity.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class AdminFindAllNoticeDTO {

    private Long id;
    private String title;
    private LocalDateTime createdAt;

    public AdminFindAllNoticeDTO(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.createdAt = notice.getCreatedAt();
    }
}
