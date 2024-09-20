package bootcamp.wssrs.domain.Notice.dto.response;

import bootcamp.wssrs.domain.Day.entity.Day;
import bootcamp.wssrs.domain.Recruit.entity.Recruit;

public record RecruitsResponseDTO(
        Long recruitId,
        String username,
        String studentId,
        String phoneNum,
        String code,
        DayDTO day,
        Boolean isUnion,
        Boolean isConfirmed
) {
    public RecruitsResponseDTO(Recruit recruit) {
        this(
                recruit.getId(),
                recruit.getMember().getUsername(),
                recruit.getMember().getStudentId(),
                recruit.getPhoneNum(),
                recruit.getCode(),
                new DayDTO(recruit.getDay()),
                recruit.getIsUnion(),
                recruit.getIsConfirmed()
        );
    }

    public record DayDTO(
            Boolean monday,
            Boolean tuesday,
            Boolean wednesday,
            Boolean thursday,
            Boolean friday,
            Boolean saturday,
            Boolean sunday
    ) {
        public DayDTO(Day day) {
            this(
                    day.getMonday(),
                    day.getTuesday(),
                    day.getWednesday(),
                    day.getThursday(),
                    day.getFriday(),
                    day.getSaturday(),
                    day.getSunday()
            );
        }
    }
}
