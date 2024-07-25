package bootcamp.wssrs.global.config;

import bootcamp.wssrs.global.jwt.JwtAuthenticationFilter;
import bootcamp.wssrs.global.jwt.TokenExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() { // security를 적용하지 않을 리소스
        return web -> web.ignoring()
                .requestMatchers("/error", "/favicon.ico");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // stateless한 rest api를 개발할 것이므로 csrf 공격에 대한 옵션은 꺼둔다.
                .csrf(AbstractHttpConfigurer::disable) // csrf 비활성화 -> cookie를 사용하지 않으면 꺼도 된다. (cookie를 사용할 경우 httpOnly(XSS 방어), sameSite(CSRF 방어)로 방어해야 한다.)
                .formLogin(AbstractHttpConfigurer::disable) // security 기본 로그인 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) // REST API이므로 basic auth 사용 x
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // cors 설정
                // 특정 URL에 대한 권한 설정.
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/swagger", "/swagger-ui/index.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/", "/api/auth/*", "/api/auth/pw/*").permitAll() // 특정 url에 대한 인가 요청 허용
                        .requestMatchers("/api/user/*").hasRole("USER") // USER 권한일 때 요청 가능.
                        .requestMatchers("/api/admin/*").hasRole("ADMIN") // ADMIN 권한일 때 요청.
                        .anyRequest().authenticated() // 그 외 요청은 인증 필요.
                )
                // Token 로그인 방식에서는 session 필요 없음.
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // addFilterBefore(after, before)
                .addFilterBefore(new TokenExceptionFilter(), jwtAuthenticationFilter.getClass()); // 토큰 예외 핸들링

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() { // 비밀번호 암호화
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PATCH", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization"); // Access-Control-Expose-Headers 사용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
