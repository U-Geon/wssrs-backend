package bootcamp.wssrs.domain.Day.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "day_id")
    @JsonIgnore
    private Long id;

    private Boolean monday;

    private Boolean tuesday;

    private Boolean wednesday;

    private Boolean thursday;

    private Boolean friday;

    private Boolean saturday;

    private Boolean sunday;

    public static Day create() {
        return Day.builder()
                .monday(false)
                .tuesday(false)
                .wednesday(false)
                .thursday(false)
                .friday(false)
                .saturday(false)
                .sunday(false)
                .build();
    }
}
