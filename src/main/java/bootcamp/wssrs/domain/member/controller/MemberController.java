package bootcamp.wssrs.domain.member.controller;


import bootcamp.wssrs.domain.member.dto.request.*;
import bootcamp.wssrs.global.jwt.TokenDTO;
import bootcamp.wssrs.domain.member.dto.response.ReissueAccessTokenResponseDTO;
import bootcamp.wssrs.domain.member.dto.response.PasswordUpdateResponseDTO;
import bootcamp.wssrs.domain.member.entity.Member;
import bootcamp.wssrs.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호 입력")
    public ResponseEntity<TokenDTO> login(@Parameter(required = true, description = "이메일, 비밀번호")
            @RequestBody @Valid LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.login(requestDTO));
    }

    // 회원가입
    @PostMapping("/sign-up")
    @Operation(summary = "회원 가입", description = "이메일, 학번, 이름, 비밀번호 입력해서 회원가입")
    public ResponseEntity<Member> signup(@Parameter(required = true, description = "이메일, 학번, 이름, 비밀번호")
                                             @RequestBody @Valid SignupRequestDTO requestDTO) {
        return ResponseEntity.ok(memberService.signUp(requestDTO));
    }

    // 회원 조회
    @PostMapping("/pw/findMember")
    @Operation(summary = "회원 조회", description = "비밀번호 찾기를 위한 회원 조회")
    public ResponseEntity<TokenDTO> findMember(@RequestBody @Valid FindMemberRequestDTO requestDTO) {
        return ResponseEntity.ok().body(memberService.findMember(requestDTO));
    }


    // 비밀번호 재설정
    @PostMapping("/pw/update")
    @Operation(summary = "비밀번호 재설정", description = "header : access token 필요")
    public ResponseEntity<PasswordUpdateResponseDTO> updatePassword(
            @Parameter(required = true, description = "새 비밀번호 입력")
            @RequestBody @Valid PasswordUpdateRequestDTO requestDTO,
            @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(memberService.updatePassword(member.getEmail(), requestDTO));
    }

    // access token 재발급
    @PostMapping("/refresh")
    @Operation(summary = "Access Token 재발급", description = "header: access token 필요 + body: refresh token")
    public ResponseEntity<ReissueAccessTokenResponseDTO> reissueAccessToken(
            @Parameter(required = true, description = "Refresh Token")
            @RequestBody @Valid ReissueAccessTokenRequestDTO requestDTO,
            @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(memberService.reissueAccessToken(requestDTO.getRefreshToken(), member));
    }

    // logout
    @GetMapping("/logout")
    @Operation(summary = "로그아웃", description = "액세스 토큰 + 리프레시 토큰 제거")
    public void logout(@RequestBody @Valid TokenDTO tokenDTO) {
        memberService.logout(tokenDTO);
    }
}
