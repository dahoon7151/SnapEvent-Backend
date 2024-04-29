package com.example.snapEvent.board.dto;

import com.example.snapEvent.board.entity.Comment;
import com.example.snapEvent.board.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponseDto {
    private Long id;
    private String nickname;
    private String content;
    private boolean myComment;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getCommentContent();
    }
}
