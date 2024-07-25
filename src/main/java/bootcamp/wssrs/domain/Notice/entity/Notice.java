package bootcamp.wssrs.domain.Notice.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;
}
