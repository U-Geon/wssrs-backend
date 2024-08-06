package bootcamp.wssrs.domain.Notice.dto.response;


import bootcamp.wssrs.domain.Day.entity.Day;
import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Recruit.entity.Recruit;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class RecruitsResponseDTO {
    private Long recruitId;
    private String username;
    private String studentId;
    private String phoneNum;
    private String code;
    private DayDTO day;
    private Boolean isUnion;
    private Boolean isConfirmed;

    public RecruitsResponseDTO(Recruit recruit) {
        Member member = recruit.getMember();
        this.recruitId = recruit.getId();
        this.username = member.getUsername();
        this.studentId = member.getStudentId();
        this.phoneNum = recruit.getPhoneNum();
        this.code = recruit.getCode();
        this.day = new DayDTO(recruit.getDay());
        this.isUnion = recruit.getIsUnion();
        this.isConfirmed = recruit.getIsConfirmed();
    }

    @Getter @Setter
    static class DayDTO {
        private Boolean monday;
        private Boolean tuesday;
        private Boolean wednesday;
        private Boolean thursday;
        private Boolean friday;
        private Boolean saturday;
        private Boolean sunday;

        public DayDTO(Day day) {
            this.monday = day.getMonday();
            this.tuesday = day.getTuesday();
            this.wednesday = day.getWednesday();
            this.thursday = day.getThursday();
            this.friday = day.getFriday();
            this.saturday = day.getSaturday();
            this.sunday = day.getSunday();
        }
    }
}
