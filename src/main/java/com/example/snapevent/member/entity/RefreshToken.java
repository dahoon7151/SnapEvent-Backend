package com.example.snapevent.member.entity;

import com.example.snapevent.common.entity.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "request_token")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class RefreshToken extends BaseEntity {

    @Id
    @Column(name = "TOKEN_USERNAME",nullable = false)
    private String username;

    @Column(nullable = false)
    private String refreshToken;
}
