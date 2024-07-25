package bootcamp.wssrs.global.jwt;

import bootcamp.wssrs.domain.member.entity.Member;
import bootcamp.wssrs.domain.member.service.LoadUserService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final LoadUserService loadUserService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String accessToken = resolveToken(request);
        log.info("Authentication filter 작동.");
        log.info("accessToken: {}", accessToken);

        if(accessToken == null) {
            filterChain.doFilter(request, response);
            return ;
        }

        try {
            if(jwtProvider.validate(accessToken)) {
                String studentId = jwtProvider.getStudentIdFromAccessToken(accessToken);
                Authentication authentication = getAuthentication(studentId);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenExpiredException e) {
            log.error("Access token expired.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        } catch (JWTVerificationException e) {
            log.error("JWTVerificationException: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION);
        String tokenPrefix = "Bearer ";
        if (token != null && token.startsWith(tokenPrefix)) {
            return token.substring(tokenPrefix.length());
        }
        return null;
    }

    // 인증 정보 가져오기
    private Authentication getAuthentication(String studentId) {
        Member userDetails = loadUserService.loadUserByUsername(studentId);

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
