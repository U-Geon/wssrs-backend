//package bootcamp.wssrs.domain.Member.repository;
//
//import bootcamp.wssrs.domain.Member.entity.Member;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class DBInit {
//
//    private final AuthRepository authRepository;
//    private final PasswordEncoder encoder;
//
//    @Value("${admin.email}")
//    private String email;
//
//    @Value("${admin.password}")
//    private String password;
//
//
//    @PostConstruct
//    private void postConstruct() {
//        Member admin = Member.createAdmin(email, password, encoder);
//        authRepository.save(admin);
//    }
//}