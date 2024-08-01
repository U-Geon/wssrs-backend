package bootcamp.wssrs.domain.File.entity;

import bootcamp.wssrs.domain.Notice.entity.Notice;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        File file = new File();
        file.setUrl(url);
        file.setName(filename);
        file.setNotice(notice);
        return file;
    }
}
