package com.example.snapEvent.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post {
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
