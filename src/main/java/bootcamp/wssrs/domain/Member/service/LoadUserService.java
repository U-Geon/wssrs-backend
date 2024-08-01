package bootcamp.wssrs.domain.Member.service;

import bootcamp.wssrs.domain.Member.entity.Member;
import bootcamp.wssrs.domain.Member.repository.AuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoadUserService implements UserDetailsService {

    private final AuthRepository authRepository;

    // jwt filter
    @Override
    public Member loadUserByUsername(String email) throws UsernameNotFoundException {
        return authRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("가입되어 있지 않은 유저입니다."));
    }
}
