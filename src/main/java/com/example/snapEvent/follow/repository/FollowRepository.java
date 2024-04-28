package com.example.snapEvent.follow.repository;

import com.example.snapEvent.follow.dto.FollowSimpleListDto;
import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Follow.PK> {

    // * 편의상 팔로우 패키지에서는 네이밍을 유저로 하겠음
    // 팔로잉 및 팔로워 관계
    Optional<Follow> findByToUserAndFromUser(Long toUserId, Long fromUserId);

    @Query(value = "select new com.example.snapEvent.follow.dto.FollowSimpleListDto(m.username, m.nickname)"
            + " from Follow f INNER JOIN Member m"
            + " ON f.toUser = m.id where f.fromUser = :userId")
    List<FollowSimpleListDto> findAllByFromUser(@Param("userId") Long userId);

    @Query(value = "select new com.example.snapEvent.follow.dto.FollowSimpleListDto(m.username, m.nickname)"
            + " from Follow f INNER JOIN Member m"
            + " ON f.fromUser = m.id where f.toUser = :userId")
    List<FollowSimpleListDto> findAllByToUser(@Param("userId") Long userId);

    // * 팔로워 및 팔로잉 수 조회
    Long countByToUser(Long userId);
    Long countByFromUser(Long userId);

    void deleteAllByFromUser(Long userId);
    void deleteAllByToUser(Long userId);

}
