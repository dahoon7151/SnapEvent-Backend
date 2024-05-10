package com.example.snapEvent.notification.entity;

import com.example.snapEvent.audit.BaseTimeEntity;
import com.example.snapEvent.entity.Subscription;
import com.example.snapEvent.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTIFICATION_ID")
    private Long id;
    private String token;

    @JoinColumn(name = "SUB_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Subscription subscription;

    @JoinColumn(name = "MEMBER_ID")
    @OneToOne(fetch = FetchType.LAZY)
    private Member member; // 1대1 단방향 매핑

    @Builder
    public Notification(String token) {
        this.token = token;
    }

    // 단방향 연관관계 메서드
    public void confirmMember(Member member) {
        this.member = member;
    }

}
