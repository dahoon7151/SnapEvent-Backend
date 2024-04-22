package com.example.snapEvent.common.entity;

import com.example.snapEvent.common.entity.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
    @Column(name = "POST_ID")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int like;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int comment;

//    @CreationTimestamp
//    @Column
//    private Timestamp postTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    private Board board;
}
