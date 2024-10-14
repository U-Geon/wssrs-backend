package bootcamp.wssrs.domain.Notice.dto.response;

import bootcamp.wssrs.domain.File.entity.File;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import java.time.LocalDateTime;
import java.util.List;

public record UserFindAllNoticeResponseDTO(
        Long id,
        String title,
        LocalDateTime createdAt,
        List<String> url
) {
    public UserFindAllNoticeResponseDTO(Notice notice) {
        this(
                notice.getId(),
                notice.getTitle(),
                notice.getCreatedAt(),
                notice.getFiles().stream().map(File::getUrl).toList()
        );
    }
}
