package bootcamp.wssrs.domain.Notice.controller;


import bootcamp.wssrs.domain.Notice.dto.request.UserRecruitRequestDTO;
import bootcamp.wssrs.domain.Notice.dto.response.UserFindAllNoticeResponseDTO;
import bootcamp.wssrs.domain.Notice.entity.Notice;
import bootcamp.wssrs.domain.Notice.service.UserNoticeService;
import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Recruit.service.RecruitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserNoticeController {

    private final UserNoticeService userNoticeService;
    private final RecruitService recruitService;

    @GetMapping("/notice") // 07.30 수정 테스트 필요
    @Operation(summary = "공고 목록", description = "토큰 필요")
    public ResponseEntity<List<UserFindAllNoticeResponseDTO>> findAll(@AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(userNoticeService.findAll());
    }

    @GetMapping("/notice/{noticeId}") // 07.30 수정 테스트 필요.
    @Operation(summary = "상세 공고", description = "토큰 필요, 에러 시 null 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<Notice> findById(
            @AuthenticationPrincipal Member member,
            @Parameter(required = true, description = "공고 id" ,in = ParameterIn.PATH)
            @PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok().body(userNoticeService.findDetailNotice(noticeId));
    }

    @PostMapping("/recruit/{noticeId}")
    @Operation(summary = "공고 지원", description = "토큰 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "NOTICE-001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<String> recruit(
            @AuthenticationPrincipal Member member,
            @RequestBody @Valid UserRecruitRequestDTO requestDTO,
            @Parameter(required = true, description = "공고 id" ,in = ParameterIn.PATH)
            @PathVariable("noticeId") Long noticeId) {

        recruitService.create(requestDTO, member, noticeId);
        return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
    }
}