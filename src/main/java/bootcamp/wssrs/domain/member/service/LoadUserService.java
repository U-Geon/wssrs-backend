package bootcamp.wssrs.domain.member.service;

import bootcamp.wssrs.domain.member.entity.Member;
import bootcamp.wssrs.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadUserService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // jwt filter
    @Override
    public Member loadUserByUsername(String studentId) throws UsernameNotFoundException {
        return memberRepository.findByEmail(studentId)
                .orElseThrow(() -> new UsernameNotFoundException("가입되어 있지 않은 유저입니다."));
    }
}
