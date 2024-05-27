package com.example.snapEvent.member.security.OAuth2;

import com.example.snapEvent.member.dto.JwtToken;
import com.example.snapEvent.member.entity.RefreshToken;
import com.example.snapEvent.member.security.jwt.JwtTokenProvider;
import com.example.snapEvent.member.repository.RefreshTokenRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response
            , Authentication authentication) throws IOException, ServletException {
        log.info("Oauth2 SuccessHandler 실행");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String username = String.valueOf(oAuth2User.getAttributes().get("email"));
        log.info("username : {}", username);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        refreshTokenRepository.save(toEntity(username, jwtToken.getRefreshToken()));

        response.addHeader("Authorization", "Bearer " + jwtToken.getAccessToken());
        log.info("헤더에 JWT 반환 성공");

        Cookie cookie = new Cookie("refreshToken", jwtToken.getRefreshToken());
        cookie.setDomain("snapevent.site");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

//        ObjectMapper objectMapper = new ObjectMapper();
//        String jwtTokenJson = objectMapper.writeValueAsString(jwtToken);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//        response.getWriter().write(jwtTokenJson);

        // 리다이렉트 반환  ////////////////////////////////////////////////////////// --- 수정 중
        String targetUrl = UriComponentsBuilder.fromUriString("https://snapevent.site/main")
                .queryParam("accessToken", jwtToken.getAccessToken())
                .build().toUriString();

        response.sendRedirect(targetUrl);
    }

    public RefreshToken toEntity(String username, String refreshToken) {

        return RefreshToken.builder()
                .username(username)
                .refreshToken(refreshToken)
                .build();
    }
}
