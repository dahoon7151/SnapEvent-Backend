package com.example.snapEvent.post.controller;

import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.common.entity.Post;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.post.dto.PostDto;
import com.example.snapEvent.post.dto.PostListDto;
import com.example.snapEvent.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts/{page}/{pageCount}/{order}")
    public ResponseEntity<Page<PostListDto>> posts(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "pageCount") int pageCount,
            @PathVariable(value = "order") String order) {
        Page<Post> postList = postService.sortPostlist(page, pageCount, order);

        Page<PostListDto> postListDto = postList.map(PostListDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(postListDto);
    }

    @PostMapping("/post")
    public ResponseEntity<String> post(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody @Valid PostDto postDto) {
        Member member = customUserDetail.getMember();
        log.info("작성자 : {}", member.getNickname());
        postService.writePost(member, postDto);

        return ResponseEntity.status(HttpStatus.OK).body("글 저장 완료");
    }
}
