package bootcamp.wssrs.domain.Member.controller;


import bootcamp.wssrs.domain.Member.dto.request.*;
import bootcamp.wssrs.domain.Member.dto.response.LoginResponseDTO;
import bootcamp.wssrs.global.jwt.TokenDTO;
import bootcamp.wssrs.domain.Member.dto.response.ReissueAccessTokenResponseDTO;
import bootcamp.wssrs.domain.Member.dto.response.PasswordUpdateResponseDTO;
import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Member.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인", description = "이메일, 비밀번호 입력")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-002", description = "비밀번호가 일치하지 않습니다..", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }

    // 회원가입
    @PostMapping("/sign-up")
    @Operation(summary = "회원 가입", description = "이메일, 학번, 이름, 비밀번호 입력해서 회원가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-003", description = "이미 가입된 계정입니다.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-004", description = "누락된 정보가 있는지 확인해주세요.", content = @Content(mediaType = "application/json")),
    })
    public ResponseEntity<Member> signup(@RequestBody @Valid SignupRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.signUp(requestDTO));
    }

    // 회원 조회
    @PostMapping("/pw/findMember")
    @Operation(summary = "회원 조회", description = "비밀번호 찾기를 위한 회원 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<TokenDTO> findMember(@RequestBody @Valid FindMemberRequestDTO requestDTO) {
        return ResponseEntity.ok().body(authService.findMember(requestDTO));
    }


    // 비밀번호 재설정
    @PostMapping("/pw/update")
    @Operation(summary = "비밀번호 재설정", description = "header : access token 필요")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<PasswordUpdateResponseDTO> updatePassword(
            @RequestBody @Valid PasswordUpdateRequestDTO requestDTO,
            @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(authService.updatePassword(member.getEmail(), requestDTO));
    }

    // access token 재발급
    @PostMapping("/refresh")
    @Operation(summary = "Access Token 재발급", description = "header: access token 필요 + body: refresh token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청에 성공", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "AUTH-001", description = "사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<ReissueAccessTokenResponseDTO> reissueAccessToken(
            @RequestBody @Valid ReissueAccessTokenRequestDTO requestDTO,
            @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok().body(authService.reissueAccessToken(requestDTO.getRefreshToken(), member));
    }

    // logout
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "body : 액세스 토큰만 넣어주세요.")
    public ResponseEntity<String> logout(@RequestBody @Valid TokenDTO tokenDTO) {
        authService.logout(tokenDTO);
        return ResponseEntity.ok().body("{\"msg\" : \"success\"}");
    }
}
