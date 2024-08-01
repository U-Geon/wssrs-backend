package bootcamp.wssrs.domain.Notice.controller;


import bootcamp.wssrs.domain.Notice.dto.request.AdminConfirmRequestDTO;
import bootcamp.wssrs.domain.Notice.dto.response.AdminFindAllNoticeDTO;
import bootcamp.wssrs.domain.Notice.dto.response.RecruitsResponseDTO;
import bootcamp.wssrs.domain.Notice.service.AdminNoticeService;
import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Recruit.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminNoticeController {

    private final AdminNoticeService adminNoticeService;
    private final RecruitService recruitService;

    @GetMapping("/notice") // 07.30 수정 테스트 필요.
    @Operation(summary = "공고 목록", description = "토큰 필요")
    public ResponseEntity<List<AdminFindAllNoticeDTO>> findAll(
            @Parameter(required = true, description = "페이지 번호")
            @RequestParam(value = "pageNum", defaultValue = "0") Integer pageNum,
            @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(adminNoticeService.findAllAtAdmin(pageNum));
    }

    @GetMapping("/notice/{noticeId}") // 07.30 수정 테스트 필요
    @Operation(summary = "지원자 목록", description = "토큰 필요")
    public ResponseEntity<List<RecruitsResponseDTO>> findRecruitments(
            @Parameter(required = true, description = "공고 id" ,in = ParameterIn.PATH)
            @PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok().body(recruitService.findRecruitInNotice(noticeId));
    }

    @PostMapping("/notice/create")
    @Operation(summary = "공고 생성", description = "form data 전송 + 토큰 필요")
    public ResponseEntity<String> create(
            @Parameter(required = true, description = "제목")
            @RequestParam("title") String title,
            @Parameter(required = true, description = "내용")
            @RequestParam("content") String content,
            @RequestParam("files") List<MultipartFile> files) {

        adminNoticeService.create(title, content, files);
        return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
    }

    @DeleteMapping("/notice/delete/{noticeId}")
    @Operation(summary = "공고 삭제", description = "토큰 필요")
    public ResponseEntity<String> delete(
            @Parameter(required = true, description = "공고 id" ,in = ParameterIn.PATH)
            @PathVariable("noticeId") Long noticeId) {
        adminNoticeService.delete(noticeId);
        return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
    }

    @PatchMapping("/recruit/confirm")
    @Operation(summary = "근로 확정")
    public ResponseEntity<String> confirm(
            @RequestBody AdminConfirmRequestDTO requestDTO
    ) {
        recruitService.confirm(requestDTO);
        return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
    }
}
