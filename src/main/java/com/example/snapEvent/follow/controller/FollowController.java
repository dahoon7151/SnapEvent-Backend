package com.example.snapEvent.follow.controller;

import com.example.snapEvent.follow.dto.FollowSimpleListDto;
import com.example.snapEvent.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FollowController {

    private final FollowService followService;

    // * from -> to로 팔로우 추가
    @PostMapping("/follow/{toUsername}/{fromUsername}")
    public ResponseEntity<?> addFollow(@PathVariable String toUsername, @PathVariable String fromUsername) {
        Boolean result = followService.addFollow(toUsername, fromUsername);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @PostMapping("/unfollow/{toUsername}/{fromUsername}")
    public ResponseEntity<?> unFollow(@PathVariable String toUsername, @PathVariable String fromUsername) {
        Boolean result = followService.unFollow(toUsername, fromUsername);
        return new ResponseEntity<>(result, HttpStatus.FOUND);
    }

    @GetMapping("/follower/{requestingUsername}")
    public ResponseEntity<?> getFollower(@PathVariable String requestingUsername) {
        List<FollowSimpleListDto> followerList = followService.getFollowerList(requestingUsername);
        return new ResponseEntity<>(followerList, HttpStatus.OK);
    }

    @GetMapping("/following/{requestingUsername}")
    public ResponseEntity<?> getFollowing(@PathVariable String requestingUsername) {
        List<FollowSimpleListDto> followingList = followService.getFollowingList(requestingUsername);
        return new ResponseEntity<>(followingList, HttpStatus.OK);
    }


}
