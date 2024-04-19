package com.example.snapEvent.entity;

import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.common.entity.audit.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Follower extends BaseTimeEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private String followerId;
    @Column
    private String followerNickname;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member memberId;
}
