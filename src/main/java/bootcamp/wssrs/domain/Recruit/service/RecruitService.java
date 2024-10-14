package bootcamp.wssrs.domain.Recruit.service;


import bootcamp.wssrs.domain.Day.entity.Day;
import bootcamp.wssrs.domain.Day.service.DayService;
import bootcamp.wssrs.domain.Notice.dto.request.AdminConfirmRequestDTO;
import bootcamp.wssrs.domain.Notice.dto.request.UserRecruitRequestDTO;
import bootcamp.wssrs.domain.Notice.dto.response.RecruitsResponseDTO;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import bootcamp.wssrs.domain.Notice.repository.NoticeRepository;
import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Recruit.entity.Recruit;
import bootcamp.wssrs.domain.Recruit.repository.RecruitRepository;
import bootcamp.wssrs.global.exception.CustomException;
import bootcamp.wssrs.global.exception.ErrorCode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final NoticeRepository noticeRepository;
    private final DayService dayService;

    // recruit 생성
    @Transactional
    public void create(@Valid UserRecruitRequestDTO requestDTO, Member member, Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));

        Recruit recruit = Recruit.create(requestDTO);
        recruit.setMember(member);
        recruit.setNotice(notice);

        Day day = dayService.create(requestDTO.day());
        recruit.setDay(day);

        recruitRepository.save(recruit);
    }

    // 지원자 목록 조회
    public List<RecruitsResponseDTO> findRecruitInNotice(Long noticeId) {
        return recruitRepository.findByNoticeId(noticeId)
                .stream().map(RecruitsResponseDTO::new).toList();
    }

    // 근무 확정
    @Transactional
    public void confirm(AdminConfirmRequestDTO requestDTO) {
        recruitRepository.findAllById(requestDTO.recruitIds())
                .forEach(recruit -> recruit.setIsConfirmed(!recruit.getIsConfirmed()));
    }

    // 지원 삭제
    @Transactional
    public void delete(Long noticeId) {
        recruitRepository.deleteAll(recruitRepository.findByNoticeId(noticeId));
    }

}
