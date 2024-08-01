package bootcamp.wssrs.domain.Notice.service;


import bootcamp.wssrs.domain.Notice.dto.request.UserRecruitRequestDTO;
import bootcamp.wssrs.domain.Notice.dto.response.UserFindAllNoticeResponseDTO;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import bootcamp.wssrs.domain.Notice.repository.NoticeRepository;
import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Recruit.service.RecruitService;
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
public class UserNoticeService {

    private final NoticeRepository noticeRepository;
    private final RecruitService recruitService;

    // 공고 목록
    public List<UserFindAllNoticeResponseDTO> findAll() {
        return noticeRepository.findAllByOrderByCreatedAtDesc()
                .stream().map(UserFindAllNoticeResponseDTO::new).toList();
    }

    // 상세 공고
    public Notice findDetailNotice(Long noticeId) {
        return noticeRepository.findById(noticeId).orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
    }

}
