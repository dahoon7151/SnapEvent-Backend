package com.example.snapEvent.board.dto;

import com.example.snapEvent.board.entity.Comment;
import com.example.snapEvent.board.entity.Post;
import com.example.snapEvent.member.entity.Member;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {
    @NotNull(message = "댓글 내용을 입력하세요.")
    private String content;

    public Comment toEntity(Member member, Post post) {
        return Comment.builder()
                .commentContent(content)
                .post(post)
                .member(member)
                .build();
    }
}
