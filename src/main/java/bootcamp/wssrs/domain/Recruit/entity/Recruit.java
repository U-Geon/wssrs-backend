package bootcamp.wssrs.domain.Recruit.entity;


import bootcamp.wssrs.domain.Day.entity.Day;
import bootcamp.wssrs.domain.Notice.dto.request.UserRecruitRequestDTO;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import bootcamp.wssrs.domain.Member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Recruit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String phoneNum;

    @Column(nullable = false)
    private Boolean isUnion;

    @Column(nullable = false)
    private Boolean isConfirmed;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice notice;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "day_id")
    private Day day;

    public static Recruit create(UserRecruitRequestDTO requestDTO) {
        Recruit recruit = new Recruit();
        recruit.setCode(requestDTO.getCode());
        recruit.setPhoneNum(requestDTO.getPhoneNum());
        recruit.setIsUnion(requestDTO.getIsUnion());
        recruit.setIsConfirmed(false);

        return recruit;
    }
}
