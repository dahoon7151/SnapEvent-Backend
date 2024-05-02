package com.example.snapEvent.board.controller;

import com.example.snapEvent.board.dto.LikeResponseDto;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.board.entity.Post;
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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Page<PostResponseDto> postList = postService.sortPostlist(page-1, pageCount, order);

        return ResponseEntity.status(HttpStatus.OK).body(postList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> post(@PathVariable(value = "id") Long id,
                                                @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        PostResponseDto postResponseDto = postService.showPost(username, id);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @PostMapping("/write")
    public ResponseEntity<PostResponseDto> write(@AuthenticationPrincipal CustomUserDetail customUserDetail,
                                                 @RequestBody @Valid PostDto postDto) {
        String username = customUserDetail.getUser().getUsername();
        log.info("작성자 : {}", username);
        PostResponseDto postResponseDto = postService.writePost(username, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<LikeResponseDto> like(@PathVariable(value = "id") Long id,
                                                @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        LikeResponseDto likeResponseDto = postService.like(username, id);

        return ResponseEntity.status(HttpStatus.OK).body(likeResponseDto);
    }

    @PatchMapping("/{id}/modify")
    public ResponseEntity<PostResponseDto> modify(@PathVariable(value = "id") Long id,
                                                  @RequestBody @Valid PostDto postDto,
                                                  @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        PostResponseDto postResponseDto = postService.modifyPost(username, id, postDto);

        return ResponseEntity.status(HttpStatus.OK).body(postResponseDto);
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id,
                                         @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        postService.deletePost(username, id);

        return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 성공");
    }

    @GetMapping("/{id}/near")
    public ResponseEntity<PostResponseDto> nearPost(@PathVariable(value = "id") Long id) {
        PostResponseDto nearDto = postService.showNearPost(id);

        return ResponseEntity.status(HttpStatus.OK).body(nearDto);
    }
}
