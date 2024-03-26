package com.example.snapEvent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long memberId;
    @Column
    private String userId;
    @Column
    private String userPassword;
    @Column
    private String nickname;
}