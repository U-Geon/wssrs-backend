package bootcamp.wssrs.domain.Day.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
        Day day = new Day();
        day.setMonday(false);
        day.setTuesday(false);
        day.setWednesday(false);
        day.setThursday(false);
        day.setFriday(false);
        day.setSaturday(false);
        day.setSunday(false);
        return day;
    }
}
