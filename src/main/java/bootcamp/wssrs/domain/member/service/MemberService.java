package bootcamp.wssrs.domain.member.service;


import bootcamp.wssrs.domain.member.dto.request.FindMemberRequestDTO;
import bootcamp.wssrs.global.exception.DuplicatedMemberException;
import bootcamp.wssrs.global.exception.SignUpFailureException;
import bootcamp.wssrs.global.jwt.TokenDTO;
import bootcamp.wssrs.domain.member.dto.response.ReissueAccessTokenResponseDTO;
import bootcamp.wssrs.global.exception.MemberNotFoundError;
import bootcamp.wssrs.global.jwt.JwtProvider;
import bootcamp.wssrs.domain.member.dto.request.LoginRequestDTO;
import bootcamp.wssrs.domain.member.dto.request.PasswordUpdateRequestDTO;
import bootcamp.wssrs.domain.member.dto.request.SignupRequestDTO;
import bootcamp.wssrs.domain.member.dto.response.PasswordUpdateResponseDTO;
import bootcamp.wssrs.domain.member.entity.Member;
import bootcamp.wssrs.domain.member.repository.MemberRepository;
import bootcamp.wssrs.global.redis.RedisService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    // 로그인
    public TokenDTO login(LoginRequestDTO requestDTO) {
        String email = requestDTO.getEmail();
        Member member = memberRepository.findByEmail(email)
                .filter(it -> passwordEncoder.matches(requestDTO.getPassword(), it.getPassword()))
                .orElseThrow(() -> new MemberNotFoundError("Invalid username or password"));

        String role = member.getRole().toString();

        String accessToken = jwtProvider.createAccessToken(email, role);
        String refreshToken = jwtProvider.createRefreshToken();

        // refresh token 저장.
        if(redisService.getRefreshToken(email) != null) {
            redisService.deleteRefreshToken(email);
        }
        redisService.saveRefreshToken(email, refreshToken);

        return new TokenDTO(accessToken, refreshToken);
    }

    // 회원가입
    @Transactional
    public Member signUp(SignupRequestDTO requestDTO) {
        try {
            Member member = Member.create(requestDTO, passwordEncoder);
            memberRepository.save(member);
            return member;
        } catch (UnexpectedRollbackException e) {
            throw new SignUpFailureException("모든 정보를 기입해주세요.");
        } catch(DataIntegrityViolationException e) {
            throw new DuplicatedMemberException("이미 가입된 회원입니다.");
        }
    }

    // 회원 조회
    public TokenDTO findMember(FindMemberRequestDTO requestDTO) {
        Member member = memberRepository.findByEmailAndStudentId(requestDTO.getEmail(), requestDTO.getStudentId())
                .orElseThrow(() -> new MemberNotFoundError("존재하지 않는 회원입니다."));

        String accessToken = jwtProvider.createAccessToken(requestDTO.getEmail(), member.getRole().toString());

        return new TokenDTO(accessToken);
    }

    // 비밀번호 업데이트
    @Transactional
    public PasswordUpdateResponseDTO updatePassword(String email, PasswordUpdateRequestDTO requestDTO) {
        memberRepository.findByEmail(email)
                .map(member -> {
                    member.setPassword(requestDTO.getNewPassword(), passwordEncoder);	// 새 비밀번호를 암호화하도록 수정
                    return new PasswordUpdateResponseDTO(requestDTO.getNewPassword());
                })
                .orElseThrow(() -> new MemberNotFoundError("Password Update : 아이디 또는 비밀번호가 일치하지 않습니다."));
        return null;
    }

    // 액세스 토큰 재발급
    @Transactional
    public ReissueAccessTokenResponseDTO reissueAccessToken(String refreshToken, Member member) throws TokenExpiredException {

        if(jwtProvider.validate(refreshToken)) {
            String refreshTokenInRedis = redisService.getRefreshToken(member.getStudentId());
            if (refreshToken.equals(refreshTokenInRedis)) {
                String accessToken = jwtProvider.createAccessToken(member.getStudentId(), member.getRole().toString());
                return new ReissueAccessTokenResponseDTO(accessToken);
            }
        } else throw new JWTVerificationException("Refresh Token Expired");
        return null;
    }

    // 로그아웃
    @Transactional
    public void logout(TokenDTO tokenDTO) {

        String accessToken = tokenDTO.getAccessToken();
        String studentId = jwtProvider.getEmailFromAccessToken(accessToken);

        // redis에서 refresh Token 제거
        redisService.deleteRefreshToken(studentId);

        // 해당 엑세스 토큰의 남은 유효시간을 얻어서 Access Token blacklist에 등록하여 만료시키기
        Long tokenExpiration = jwtProvider.getTokenExpiration(accessToken);
        redisService.setBlackList(accessToken, "access_token", tokenExpiration);
    }
}
