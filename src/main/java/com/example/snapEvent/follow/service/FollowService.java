package com.example.snapEvent.follow.service;

import com.example.snapEvent.exception.FollowException;
import com.example.snapEvent.exception.UserException;
import com.example.snapEvent.follow.FollowStatus;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.follow.dto.FollowSimpleListDto;
import com.example.snapEvent.follow.entity.Follow;
import com.example.snapEvent.follow.repository.FollowRepository;
import com.example.snapEvent.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    /*
    요구사항: 팔로잉/팔로워 목록 조회, 팔로우 추가, 팔로우 취소
     */

    // * 팔로우 추가
    @Transactional
    public Boolean addFollow(String toUsername, String fromUsername) {
        checkSameUser(toUsername, fromUsername);

        Member toUser = memberRepository.findByUsername(toUsername).orElseThrow(UserException::new);
        Member fromUser = memberRepository.findByUsername(fromUsername).orElseThrow(UserException::new);

        Optional<Follow> relation = getFollowRelation(toUser.getId(), fromUser.getId());
        if (relation.isPresent()) {
            throw new FollowException("이미 follow 한 관계입니다.");
        }
        followRepository.save(new Follow(toUser.getId(), fromUser.getId()));
        return true;
    }

    // * 팔로우 취소
    @Transactional
    public Boolean unFollow(String toUsername, String fromUsername) {
        checkSameUser(toUsername, fromUsername);

        Member toUser = memberRepository.findByUsername(toUsername).orElseThrow(UserException::new);
        Member fromUser = memberRepository.findByUsername(fromUsername).orElseThrow(UserException::new);

        Optional<Follow> relation = getFollowRelation(toUser.getId(), fromUser.getId());
        if (relation.isEmpty()) {
            throw new FollowException("follow 관계가 아닙니다.");
        }
        followRepository.delete(relation.get());
        return true;
    }

    // * 팔로우 관계를 가져옴
    private Optional<Follow> getFollowRelation(Long toUserId, Long fromUserId) {
        return followRepository.findByToUserAndFromUser(toUserId, fromUserId);
    }

    // * 계정 삭제할 때 팔로우 관계도 모두 삭제
    @Transactional
    public void deleteFollowRelation(Long userId) {
        followRepository.deleteAllByFromUser(userId);
        followRepository.deleteAllByToUser(userId);
    }

    // * 팔로우 대상이 같은지 확인
    private void checkSameUser(String toUsername, String fromUsername) {
        if (toUsername.equals(fromUsername))
            throw new FollowException("follower 와 following 의 대상이 동일합니다.");
    }

    // * 팔로잉 목록 조회
    public List<FollowSimpleListDto> getFollowingList(String username) {
        Member user = memberRepository.findByUsername(username).orElseThrow(UserException::new);
        return followRepository.findAllByFromUser(user.getId());
    }

    // * 팔로워 목록 조회
    public List<FollowSimpleListDto> getFollowerList(String username) {
        Member user = memberRepository.findByUsername(username).orElseThrow(UserException::new);
        return followRepository.findAllByToUser(user.getId());
    }

    // * 팔로워 수 조회
    public Long getFollowerCount(String username) {
        Member user = memberRepository.findByUsername(username).orElseThrow(UserException::new);
        return followRepository.countByToUser(user.getId());
    }

    // * 팔로잉 수 조회
    public Long getFollowingCount(String username) {
        Member user = memberRepository.findByUsername(username).orElseThrow(UserException::new);
        return followRepository.countByFromUser(user.getId());
    }

}
