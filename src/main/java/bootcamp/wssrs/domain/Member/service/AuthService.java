package bootcamp.wssrs.domain.Member.service;


import bootcamp.wssrs.domain.Member.dto.request.FindMemberRequestDTO;
import bootcamp.wssrs.domain.Member.dto.response.LoginResponseDTO;
import bootcamp.wssrs.global.exception.CustomException;
import bootcamp.wssrs.global.exception.ErrorCode;
import bootcamp.wssrs.global.jwt.TokenDTO;
import bootcamp.wssrs.domain.Member.dto.response.ReissueAccessTokenResponseDTO;
import bootcamp.wssrs.global.jwt.JwtProvider;
import bootcamp.wssrs.domain.Member.dto.request.LoginRequestDTO;
import bootcamp.wssrs.domain.Member.dto.request.PasswordUpdateRequestDTO;
import bootcamp.wssrs.domain.Member.dto.request.SignupRequestDTO;
import bootcamp.wssrs.domain.Member.dto.response.PasswordUpdateResponseDTO;
import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Member.repository.AuthRepository;
import bootcamp.wssrs.global.redis.RedisService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    // 로그인
    public LoginResponseDTO login(LoginRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        Member member = authRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        if(!passwordEncoder.matches(requestDTO.getPassword(), member.getPassword())) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        String role = member.getRole().toString();

        String accessToken = jwtProvider.createAccessToken(email, role);
        String refreshToken = jwtProvider.createRefreshToken();

        // refresh token 저장.
        if(redisService.getRefreshToken(email) != null) {
            redisService.deleteRefreshToken(email);
        }
        redisService.saveRefreshToken(email, refreshToken);

        return new LoginResponseDTO(accessToken, refreshToken, member.getUsername(), member.getStudentId());
    }

    // 회원가입
    @Transactional
    public Member signUp(SignupRequestDTO requestDTO) {
        try {
            Member member = Member.createUser(requestDTO, passwordEncoder);
            authRepository.save(member);
            return member;
        } catch (IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_ARGUMENT);
        } catch(DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.DUPLICATED_MEMBER);
        }
    }

    // 회원 조회
    public TokenDTO findMember(FindMemberRequestDTO requestDTO) {
        Member member = authRepository.findByEmailAndStudentId(requestDTO.getEmail(), requestDTO.getStudentId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        String accessToken = jwtProvider.createAccessToken(requestDTO.getEmail(), member.getRole().toString());

        return new TokenDTO(accessToken);
    }

    // 비밀번호 업데이트
    @Transactional
    public PasswordUpdateResponseDTO updatePassword(String email, PasswordUpdateRequestDTO requestDTO) {
        return authRepository.findByEmail(email)
                .map(member -> {
                    member.setPassword(requestDTO.getNewPassword(), passwordEncoder);	// 새 비밀번호를 암호화하도록 수정
                    return new PasswordUpdateResponseDTO(requestDTO.getNewPassword());
                }).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    // 액세스 토큰 재발급
    @Transactional
    public ReissueAccessTokenResponseDTO reissueAccessToken(String refreshToken, Member member) {

        if(jwtProvider.validate(refreshToken)) {
            String refreshTokenInRedis = redisService.getRefreshToken(member.getEmail());
            if (refreshToken.equals(refreshTokenInRedis)) {
                String accessToken = jwtProvider.createAccessToken(member.getEmail(), member.getRole().toString());
                return new ReissueAccessTokenResponseDTO(accessToken);
            }
        } else {
            throw new JWTVerificationException("Refresh Token Expired");
        }
        return null;
    }

    // 로그아웃
    @Transactional
    public void logout(TokenDTO tokenDTO) {

        String accessToken = tokenDTO.getAccessToken();
        String email = jwtProvider.getEmailFromAccessToken(accessToken);

        log.info("access token: {}", accessToken);
        log.info("email : {}", email);

        // redis에서 refresh Token 제거
        redisService.deleteRefreshToken(email);

        // 해당 엑세스 토큰의 남은 유효시간을 얻어서 Access Token blacklist에 등록하여 만료시키기
        Long tokenExpiration = jwtProvider.getTokenExpiration(accessToken);
        redisService.setBlackList(accessToken, "logout", tokenExpiration);
    }
}
