package com.example.snapEvent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue
    private Long memberId;
    @Column
    private String userId;
    @Column
    private String userPassword;
    @Column
    private String nickname;
}