package com.example.snapEvent.entity;

import com.example.snapEvent.entity.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column
    private String userId;

    @Column
    private String userPassword;

    @Column
    private String nickname;

    @OneToMany
    @Builder.Default
    private List<Subscription> subscriptions = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<Follower> followers = new ArrayList<>();

    @OneToMany
    @Builder.Default
    private List<Notification> notifications = new ArrayList<>();
}