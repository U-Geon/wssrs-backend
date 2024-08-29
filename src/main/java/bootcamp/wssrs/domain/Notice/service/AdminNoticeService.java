package bootcamp.wssrs.domain.Notice.service;

import bootcamp.wssrs.domain.File.entity.File;
import bootcamp.wssrs.domain.File.service.FileService;
import bootcamp.wssrs.domain.Notice.dto.response.AdminFindAllNoticeDTO;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import bootcamp.wssrs.domain.Notice.repository.NoticeRepository;
import bootcamp.wssrs.domain.Recruit.service.RecruitService;
import bootcamp.wssrs.global.exception.CustomException;
import bootcamp.wssrs.global.exception.ErrorCode;
import bootcamp.wssrs.global.s3.S3FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminNoticeService {

    private final NoticeRepository noticeRepository;
    private final FileService fileService;
    private final S3FileService s3FileService;
    private final RecruitService recruitService;

    // 공고 생성
    @Transactional
    public void create(String title,
                         String content,
                         List<MultipartFile> files) {

        Notice notice = Notice.create(title, content);
        for (MultipartFile file : files) {
            fileService.create(file, notice);
        }
        noticeRepository.save(notice);
    }

    // 공고 조회
    public AdminFindAllNoticeDTO findAllAtAdmin(Integer pageNum) {
        Pageable pageable = PageRequest.of(pageNum, 8);
        Page<Notice> page = noticeRepository.findAllByOrderByCreatedAtDesc(pageable);
        Long totalCount = noticeRepository.countTotalNotices();

        return new AdminFindAllNoticeDTO(totalCount, page);
    }

    // 공고 삭제
    @Transactional
    public void delete(Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId).orElseThrow(() -> new CustomException(ErrorCode.NOTICE_NOT_FOUND));
        List<File> files = notice.getFiles();
        for (File file : files) {
            s3FileService.deleteFile("notice", file.getName());
        }
        recruitService.delete(noticeId); // notice를 지워질 때 지원 기록이 같이 지워지는지 확인
        noticeRepository.delete(notice);
    }
}
