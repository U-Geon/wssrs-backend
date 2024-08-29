package bootcamp.wssrs.domain.Notice.dto.response;

import bootcamp.wssrs.domain.Notice.entity.Notice;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class AdminFindAllNoticeDTO {
    private Long totalCount;
    private List<AdminFindAllNoticeInnerClass> notices;

    public AdminFindAllNoticeDTO(Long totalCount, Page<Notice> pages) {
        this.notices = pages.stream().map(AdminFindAllNoticeInnerClass::new).toList();
        this.totalCount = totalCount;
    }

    @Getter @Setter
    static class AdminFindAllNoticeInnerClass {
        private Long id;
        private String title;
        private LocalDateTime createdAt;

        public AdminFindAllNoticeInnerClass(Notice notice) {
            this.id = notice.getId();
            this.title = notice.getTitle();
            this.createdAt = notice.getCreatedAt();
        }
    }
}
