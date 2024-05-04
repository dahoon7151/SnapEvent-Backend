package com.example.snapEvent.member.entity;

import com.example.snapEvent.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TOKEN")
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
