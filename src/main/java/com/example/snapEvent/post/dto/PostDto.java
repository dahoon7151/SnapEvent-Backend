package com.example.snapEvent.post.dto;

import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.common.entity.Post;
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
                .like(0)
                .comment(0)
                .member(member)
                .build();
    }
}
