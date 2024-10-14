package bootcamp.wssrs.domain.Notice.dto.response;

import bootcamp.wssrs.domain.Notice.entity.Notice;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;

public record AdminFindAllNoticeDTO(
        Long totalCount,
        List<AdminFindAllNoticeInnerClass> notices
) {
    public AdminFindAllNoticeDTO(Long totalCount, Page<Notice> pages) {
        this(totalCount, pages.stream().map(AdminFindAllNoticeInnerClass::new).toList());
    }

    public record AdminFindAllNoticeInnerClass(
            Long id,
            String title,
            LocalDateTime createdAt
    ) {
        public AdminFindAllNoticeInnerClass(Notice notice) {
            this(notice.getId(), notice.getTitle(), notice.getCreatedAt());
        }
    }
}
