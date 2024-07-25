package bootcamp.wssrs.domain.member.service;

import bootcamp.wssrs.domain.member.dto.request.LoginRequestDTO;
import bootcamp.wssrs.domain.member.dto.request.SignupRequestDTO;
import bootcamp.wssrs.global.jwt.TokenDTO;
import bootcamp.wssrs.domain.member.entity.Member;
import bootcamp.wssrs.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();

        SignupRequestDTO req = new SignupRequestDTO();
        req.setStudentId("20203103");
        req.setUsername("류건");
        req.setPassword("1234");
        memberService.signUp(req);

    }

    @Test
    public void signUp() {
        // give
        SignupRequestDTO req = new SignupRequestDTO();
        req.setStudentId("20203103");
        req.setUsername("류건");
        req.setPassword("1234");

        // whenLoginRequestDTO req = new LoginRequestDTO();
        Member member = memberService.signUp(req);

        // then
        Assertions.assertEquals(member.getStudentId(), req.getStudentId());
    }

    @Test
    void testLogin() {
        // give

    }

    @Test
    void updatePassword() {
    }

    @Test
    void reissueAccessToken() {
    }
}