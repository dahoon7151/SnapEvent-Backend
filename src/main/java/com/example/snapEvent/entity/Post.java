package com.example.snapEvent.entity;

import com.example.snapEvent.audit.BaseEntity;
import com.example.snapEvent.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long postId;
    @Column
    private String title;
    @Column
    private String content;
    @CreationTimestamp
    @Column
    private Timestamp postTime;
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member memberId;
    @ManyToOne
    @JoinColumn(name = "boardId")
    private Board boardId;
}
