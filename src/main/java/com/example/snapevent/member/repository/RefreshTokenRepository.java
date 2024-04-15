package com.example.snapevent.member.repository;

import com.example.snapevent.member.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUsername(String username);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
