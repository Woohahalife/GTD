package com.core.gtd.src.common.filter;

import com.core.gtd.src.common.exception.AppException;
import com.core.gtd.src.common.security.jwt.JwtTokenGenerator;
import com.core.gtd.src.common.security.principal.PrincipalDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.core.gtd.src.common.response.ResponseStatus.INVALID_ERROR;
import static com.core.gtd.src.common.response.ResponseStatus.NOT_BEARER_TOKEN;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String BEARER = "Bearer ";
    private static final String PUBLIC_API_PREFIX = "/public-api";

    private final JwtTokenGenerator jwtTokenGenerator;
    private final PrincipalDetailService principalDetailService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if(checkPublicApi(request, response, filterChain)) {
            System.out.println("필터 작동 X");
            return;
        }

        try {
            String accessToken = parseBearerToken(request);
            UserDetails userDetails = parseUsername(accessToken);
            configureAuthenticatedUser(userDetails);

        }catch (Exception e) {
            throw new AppException(INVALID_ERROR);
        }

        filterChain.doFilter(request, response);
    }

    private void configureAuthenticatedUser(UserDetails userDetails) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authToken);
    }


    private String parseBearerToken(HttpServletRequest request) {

        String authorization = extractAuthorization(request);
        log.info("[JwtTokenFilter] Extract authorization for Jwt token: {}", authorization);
        return authorization.split(" ")[1];
    }

    private String extractAuthorization(HttpServletRequest request) {
        // request에서 Authorization 헤더를 찾음
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        //Authorization 헤더 검증
        if(authorization == null || !authorization.startsWith(BEARER)) { // authorization이 null이거나 "bearer "가 아닌 경우 필터링
            throw new AppException(NOT_BEARER_TOKEN);
        }
        return authorization;
    }

    // accessToken으로부터 Username을 추출해 UserDetail로 넘김
    private UserDetails parseUsername(String accessToken) {
        String username = jwtTokenGenerator.getUsername(accessToken);
        return principalDetailService.loadUserByUsername(username);
    }

    // HTTP 요청이 public-api로 온 요청인지 확인
    private boolean checkPublicApi(HttpServletRequest request,
                                   HttpServletResponse response,
                                   FilterChain filterChain) throws IOException, ServletException {

        System.out.println("요청 URI : " + request.getRequestURI());

        if(request.getRequestURI().startsWith(PUBLIC_API_PREFIX) ||
           request.getRequestURI().contains("h2-console") ||
           request.getRequestURI().contains("swagger"))
        {
            filterChain.doFilter(request, response);
            return true;
        }
        return false;
    }
}
