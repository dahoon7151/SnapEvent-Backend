package com.example.snapevent.entity;

import com.example.snapevent.entity.audit.BaseTimeEntity;
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
    private Long memberId;
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