package com.example.snapEvent.board.entity;

import com.example.snapEvent.audit.BaseEntity;
import com.example.snapEvent.board.dto.CommentDto;
import com.example.snapEvent.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Comment extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column(name = "COMMENT_ID")
    private Long id;

    @Column
    private String commentContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_ID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    public Comment update(CommentDto commentDto) {
        this.commentContent = commentDto.getContent();

        return this;
    }
}
