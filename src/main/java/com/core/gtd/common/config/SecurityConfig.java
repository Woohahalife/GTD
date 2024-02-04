package com.core.gtd.common.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // 개발 편의를 위한 disable 처리
        http
                .csrf(auth -> auth.disable())
                .httpBasic(auth -> auth.disable())
                .cors(auth -> auth.disable()
                );

        // 개발 편의를 위한 전 엔드 포인트 접근 허용 -> security 인가 제어 해제
        http
                .authorizeHttpRequests(
                    auth -> auth
                            .requestMatchers("/**").permitAll()
                            .anyRequest().permitAll()
                );

        // JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정
        http
                .sessionManagement(
                        session -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    // H2Console에 대한 필터를 차단(spring.h2.console.enabled = true 일 때만 작동)
    @Bean
    @ConditionalOnProperty(name = "spring.h2.console.enabled",havingValue = "true")
    public WebSecurityCustomizer configureH2ConsoleEnable() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console());
    }
}
