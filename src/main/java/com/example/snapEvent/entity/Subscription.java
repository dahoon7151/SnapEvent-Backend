package com.example.snapEvent.entity;

import com.example.snapEvent.audit.BaseTimeEntity;
import com.example.snapEvent.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Subscription extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SUB_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String userName; // userPassword는 FCM 토큰으로 대체

    // 연관관계 편의 메서드
    public void addMember(Member member) {
        this.member = member;
        member.getSubscriptions().add(this);
    }

    public void removeMember(Member member) {
        this.member = null;
        member.getSubscriptions().remove(this);
    }

}
