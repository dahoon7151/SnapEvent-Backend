package com.example.snapEvent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long commentId;
    @Column
    private String commentContent;
    @CreationTimestamp
    @Column
    private Timestamp commentTime;
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post postId;
    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member memberId;
}
