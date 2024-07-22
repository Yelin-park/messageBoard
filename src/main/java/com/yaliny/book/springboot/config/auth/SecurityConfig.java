package com.yaliny.book.springboot.config.auth;

import com.yaliny.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((csrf) -> csrf.disable())
            .headers((headerConfig) ->
                headerConfig.frameOptions(frameOptionsConfig ->
                    frameOptionsConfig.disable()
                )
            )
            // antMatchers이 지원되지 않음. requestMatchers 사용함
            .authorizeRequests((authorizeRequests) ->
                authorizeRequests
                    .requestMatchers("/", "/css/**", "images/**", "/js/**", "/h2-console/**").permitAll()
                    .requestMatchers("/api/v1/**").hasRole(Role.USER.name())
                    .anyRequest().authenticated()
            )
            .logout((logoutConfig) -> logoutConfig.logoutSuccessUrl("/"))
            .oauth2Login((oauth2) -> oauth2
                .loginPage("/oauth2/authorization/google") // 권한 접근 실패시 로그인 페이지로 이동
                .defaultSuccessUrl("http://localhost:8081") // 로그인 성공시 이동할 페이지
                .failureUrl("/oauth2/authorization/google") // 로그인 실패시 이동할 페이지
                .userInfoEndpoint((userInfoEndpoint) ->
                    userInfoEndpoint.userService(customOAuth2UserService)
                )
            );

        return http.build();
    }

}
