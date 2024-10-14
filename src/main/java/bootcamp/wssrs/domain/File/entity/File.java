package bootcamp.wssrs.domain.File.entity;

import bootcamp.wssrs.domain.Notice.entity.Notice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    @JsonIgnore
    private Notice notice;

    private void setNotice(Notice notice) {
        this.notice = notice;
        notice.getFiles().add(this);
    }

    public static File create(String url, String filename, Notice notice) {
        File file = File.builder()
                .url(url)
                .name(filename)
                .build();
        file.setNotice(notice);
        return file;
    }
}
