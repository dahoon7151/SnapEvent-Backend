package com.example.snapEvent.subscribe.entity;

import com.example.snapEvent.audit.BaseTimeEntity;
import com.example.snapEvent.entity.Site;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.subscribe.SiteName;
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

    private String username; // userPassword는 FCM 토큰으로 대체

    @Column(nullable = false)
    private SiteName siteName;
}
