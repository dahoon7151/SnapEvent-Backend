package com.example.snapEvent.entity;

import com.example.snapEvent.audit.BaseTimeEntity;
import com.example.snapEvent.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follower extends BaseTimeEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "FOLLOWER_ID")
    private String id;

    @Column
    private String followerNickname;

    @ElementCollection(fetch = FetchType.LAZY)
    private List<String> subList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;
}
