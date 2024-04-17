package com.example.snapevent.entity;

import com.example.snapevent.entity.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {
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
