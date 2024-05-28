package com.example.snapEvent.board.controller;

import com.example.snapEvent.board.dto.CommentDto;
import com.example.snapEvent.board.dto.CommentResponseDto;
import com.example.snapEvent.board.entity.Comment;
import com.example.snapEvent.board.entity.Post;
import com.example.snapEvent.board.service.CommentService;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.security.CustomUserDetail;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentResponseDto>> comments(@PathVariable(value = "postId") Long postId,
                                                             @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        log.info("controller 댓글 조회");
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        List<CommentResponseDto> commentResponseDto = commentService.showCommentlist(username, postId);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @PostMapping("/{postId}/write")
    public ResponseEntity<CommentResponseDto> write(@PathVariable(value = "postId") Long postId,
                                                    @AuthenticationPrincipal CustomUserDetail customUserDetail,
                                                    @RequestBody @Valid CommentDto commentDto) {
        log.info("controller 댓글 작성");
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        CommentResponseDto commentResponseDto = commentService.writeComment(username, postId, commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @PatchMapping("/{commentId}/modify")
    public ResponseEntity<CommentResponseDto> modify(@PathVariable(value = "commentId") Long commentId,
                                                     @AuthenticationPrincipal CustomUserDetail customUserDetail,
                                                     @RequestBody @Valid CommentDto commentDto) {
        log.info("controller 댓글 수정");
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        CommentResponseDto commentResponseDto = commentService.modifyComment(username, commentId, commentDto);

        return ResponseEntity.status(HttpStatus.OK).body(commentResponseDto);
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<String> delete(@PathVariable(value = "commentId") Long commentId,
                                         @AuthenticationPrincipal CustomUserDetail customUserDetail) {
        log.info("controller 댓글 삭제");
        String username = customUserDetail.getUser().getUsername();
        log.info("사용자 : {}", username);

        commentService.deleteComment(username, commentId);

        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공");
    }
}
