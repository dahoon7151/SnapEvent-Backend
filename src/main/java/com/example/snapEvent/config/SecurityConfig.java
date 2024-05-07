package com.example.snapEvent.config;

import com.example.snapEvent.member.security.CustomAuthenticationEntryPoint;
import com.example.snapEvent.member.security.OAuth2.OAuth2SuccessHandler;
import com.example.snapEvent.member.security.jwt.ExceptionHandlerFilter;
import com.example.snapEvent.member.security.jwt.JwtAuthenticationFilter;
import com.example.snapEvent.member.security.jwt.JwtTokenProvider;
import com.example.snapEvent.member.repository.RefreshTokenRepository;
import com.example.snapEvent.member.security.OAuth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ExceptionHandlerFilter exceptionHandlerFilter;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                // REST API이므로 basic auth 및 csrf 보안을 사용하지 않음
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                // JWT를 사용하기 때문에 세션을 사용하지 않음
                .sessionManagement(sessionManagementConfigurer -> sessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // 해당 API에 대해서는 모든 요청을 허가
                        .requestMatchers("/oauth2/authorization/google",
                                "/oauth2/authorization/naver",
                                "/oauth2/authorization/kakao",
                                "api/members/join",
                                "api/members/login",
                                "api/members/reissue",
                                "api/members/checkname").permitAll()
                        // USER 권한이 있어야 요청할 수 있음
                        .requestMatchers("api/members/test").hasRole("USER")
//                        // 이 밖에 모든 요청에 대해서 인증을 필요로 한다는 설정
                        .anyRequest().authenticated())
//                        .anyRequest().permitAll()) // 테스트용 허용
//                .formLogin(login -> login
//                        .loginPage("/")                       //로그인 페이지 url
////                        .loginProcessingUrl("/members/login") //이 url을 로그인 기능을 담당하게 함
//                        .defaultSuccessUrl("/"))              // 성공하면 이 url로 가게 함
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2SuccessHandler(jwtTokenProvider, refreshTokenRepository))
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService)))
                // JWT 인증을 위하여 직접 구현한 필터를 UsernamePasswordAuthenticationFilter 전에 실행
                .addFilterAfter(new UsernamePasswordAuthenticationFilter(), LogoutFilter.class)
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtAuthenticationFilter.class)
                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()))
                .build();
    }
}