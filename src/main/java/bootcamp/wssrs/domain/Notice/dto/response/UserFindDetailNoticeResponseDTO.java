package bootcamp.wssrs.domain.Notice.dto.response;


import bootcamp.wssrs.domain.Notice.entity.Notice;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserFindDetailNoticeResponseDTO {
    private String username;
    private String studentId;
    private Notice notice;

    public UserFindDetailNoticeResponseDTO(String username, String studentId, Notice notice) {
        this.username = username;
        this.studentId = studentId;
        this.notice = notice;
    }
}
