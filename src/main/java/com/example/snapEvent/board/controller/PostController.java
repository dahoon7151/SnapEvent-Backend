package com.example.snapEvent.board.controller;

import com.example.snapEvent.board.dto.LikeResponseDto;
import com.example.snapEvent.common.entity.Member;
import com.example.snapEvent.common.entity.Post;
import com.example.snapEvent.member.security.CustomUserDetail;
import com.example.snapEvent.board.dto.PostDto;
import com.example.snapEvent.board.dto.PostResponseDto;
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
    public ResponseEntity<Page<PostResponseDto>> posts(@PathVariable(value = "page") int page,
                                                       @PathVariable(value = "pageCount") int pageCount,
                                                       @PathVariable(value = "order") String order) {
        Page<Post> postList = postService.sortPostlist(page, pageCount, order);

        Page<PostResponseDto> postResponseDto = postList.map(PostResponseDto::new);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> post(@PathVariable(value = "id") Long id,
                                                @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        Member member = customUserDetail.getMember();
        log.info("사용자 : {}", member.getUsername());

        PostResponseDto postResponseDto = postService.showPost(member, id);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @PostMapping("/write")
    public ResponseEntity<PostResponseDto> write(@AuthenticationPrincipal CustomUserDetail customUserDetail,
                                                 @RequestBody @Valid PostDto postDto) {
        Member member = customUserDetail.getMember();
        log.info("작성자 : {}", member.getNickname());
        PostResponseDto postResponseDto = postService.writePost(member, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<LikeResponseDto> like(@PathVariable(value = "id") Long id,
                                                @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        Member member = customUserDetail.getMember();
        log.info("사용자 : {}", member.getUsername());

        LikeResponseDto likeResponseDto = postService.like(member, id);

        return ResponseEntity.status(HttpStatus.OK).body(likeResponseDto);
    }

    //게시물 수정
}
