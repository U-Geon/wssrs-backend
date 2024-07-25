package bootcamp.wssrs.domain.member.entity;


import bootcamp.wssrs.domain.member.dto.request.SignupRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.Collections;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotNull
    @Column(unique = true)
    private String studentId;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
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

    public static Member create(SignupRequestDTO request, PasswordEncoder encoder) {
        Member member = new Member();
        member.setStudentId(request.getStudentId());
        member.setPassword(encoder.encode(request.getPassword()));
        member.setUsername(request.getUsername());
        member.setEmail(request.getEmail());
        member.setRole(Role.USER);
        return member;
    }
}
