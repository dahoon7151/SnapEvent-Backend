package com.example.snapEvent.board.controller;

import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.common.entity.Post;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.board.dto.PostDto;
import com.example.snapEvent.board.dto.PostListDto;
import com.example.snapEvent.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    @GetMapping("/{page}/{pageCount}/{order}")
    public ResponseEntity<Page<PostListDto>> posts(
            @PathVariable(value = "page") int page,
            @PathVariable(value = "pageCount") int pageCount,
            @PathVariable(value = "order") String order) {
        Page<Post> postList = postService.sortPostlist(page, pageCount, order);

        Page<PostListDto> postListDto = postList.map(PostListDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(postListDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostListDto> post(@PathVariable(value = "id") Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/write")
    public ResponseEntity<PostListDto> write(@AuthenticationPrincipal CustomUserDetail customUserDetail, @RequestBody @Valid PostDto postDto) {
        Member member = customUserDetail.getMember();
        log.info("작성자 : {}", member.getNickname());
        PostListDto postListDto = postService.writePost(member, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postListDto);
    }
}
