package com.example.snapevent.member.OAuth2;

import com.example.snapevent.member.dto.JwtToken;
import com.example.snapevent.member.entity.RefreshToken;
import com.example.snapevent.member.jwt.JwtTokenProvider;
import com.example.snapevent.member.repository.RefreshTokenRepository;
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
            , Authentication authentication) throws IOException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String username = oAuth2User.getAttribute("email");

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
        refreshTokenRepository.save(toEntity(username, jwtToken.getRefreshToken()));

        response.addHeader("Authorization", "Bearer " + jwtToken.getAccessToken());

        String targetUrl = UriComponentsBuilder.fromUriString("/")
                .queryParam("token", jwtToken.getAccessToken())
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public RefreshToken toEntity(String username, String refreshToken) {

        return RefreshToken.builder()
                .username(username)
                .refreshToken(refreshToken)
                .build();
    }
}
