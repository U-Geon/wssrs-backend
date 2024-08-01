package bootcamp.wssrs.domain.Notice.dto.response;

import bootcamp.wssrs.domain.File.entity.File;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class UserFindAllNoticeResponseDTO {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private List<String> url;

    public UserFindAllNoticeResponseDTO(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.createdAt = notice.getCreatedAt();
        this.url = notice.getFiles().stream().map(File::getUrl).toList();
    }
}
