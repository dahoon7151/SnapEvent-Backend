package com.example.snapEvent.board.dto;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.board.entity.Post;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {
    @NotNull(message = "글 제목을 입력하세요.")
    private String title;

    @NotNull(message = "글 내용을 입력하세요.")
    private String content;

    public Post toEntity(Member member) {
        return Post.builder()
                .title(title)
                .content(content)
                .likeCount(0)
                .commentCount(0)
                .member(member)
                .build();
    }
}
