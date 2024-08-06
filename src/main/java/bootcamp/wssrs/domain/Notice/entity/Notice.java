package bootcamp.wssrs.domain.Notice.entity;


import bootcamp.wssrs.domain.File.entity.File;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "notice", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

    public static Notice create(String title, String content) {
        Notice notice = new Notice();
        notice.title = title;
        notice.content = content;

        return notice;
    }
}
