package com.example.snapEvent.member.security.jwt;

import com.example.snapEvent.member.dto.JwtToken;
import com.example.snapEvent.member.repository.RefreshTokenRepository;
import com.example.snapEvent.member.security.CustomUserDetail;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {
    private final Key key;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // properties 파일에서 jwt.secret 값 가져와서 key에 저장
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // Member 정보를 가지고 AccessToken, RefreshToken을 생성하는 메서드
    public JwtToken generateToken(Authentication authentication) {
        log.info("토큰 생성 메소드 실행");
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        // Access Token 생성 (1시간)
        Date accessTokenExpiresIn = new Date(now + 86400000/24);
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("auth", authorities)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Refresh Token 생성 (7일)
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000*7))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Jwt 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // Jwt 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        // CustomUserDetails 객체에 권한 정보가 저장될 수 있도록 변경
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        log.info("loadUserByUsername 메소드 실행 완료");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

        // 클레임에서 권한 정보 가져오기
//        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        // UserDetails 객체를 만들어서 Authentication return
//        // UserDetails: interface, User: UserDetails를 구현한 class
//        UserDetails principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
            throw new TokenNotValidateException("유효하지 않은 토큰입니다.", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
//            refreshTokenRepository.delete(refreshTokenRepository.findByRefreshToken(token)
//                    .orElseThrow(() -> new TokenNotValidateException("만료된 토큰입니다.", e)));
            throw new TokenNotValidateException("만료된 토큰입니다.", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
            throw new TokenNotValidateException("유효하지 않은 토큰입니다.", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty.", e);
            throw new TokenNotValidateException("유효하지 않은 토큰입니다.", e);
        }
    }


    // accessToken
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}