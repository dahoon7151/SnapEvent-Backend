package com.example.snapEvent.subscribe.repository;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.subscribe.SiteName;
import com.example.snapEvent.subscribe.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    List<Subscription> findAllByMember(Member member);

    Optional<Subscription> findByMemberAndSiteName(Member member, SiteName siteName);

    Optional<Subscription> findBySiteName(SiteName siteName);
}
