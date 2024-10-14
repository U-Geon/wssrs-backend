package bootcamp.wssrs.domain.Member.entity;


import bootcamp.wssrs.domain.Member.dto.request.SignupRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String studentId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public void setPassword(@NotNull String password, PasswordEncoder encoder) {
        this.password = encoder.encode(password);
    }

    public static Member createUser(@NotNull SignupRequestDTO request, PasswordEncoder encoder) {
        return Member.builder()
                .studentId(request.studentId())
                .password(encoder.encode(request.password()))
                .username(request.username())
                .email(request.email())
                .role(Role.USER)
                .build();
    }

    public static Member createAdmin(String email, String password, PasswordEncoder encoder) {
        return Member.builder()
                .studentId("admin")
                .password(encoder.encode(password))
                .username("생활협동조합")
                .email(email)
                .role(Role.ADMIN)
                .build();
    }
}
