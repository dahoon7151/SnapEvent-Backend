package com.example.snapEvent.subscribe.service;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.member.repository.MemberRepository;
import com.example.snapEvent.subscribe.SiteName;
import com.example.snapEvent.subscribe.dto.SubscribeDto;
import com.example.snapEvent.subscribe.dto.SubscribeResponseDto;
import com.example.snapEvent.subscribe.entity.Subscription;
import com.example.snapEvent.subscribe.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService{
    private final SubscriptionRepository subscriptionRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public List<SubscribeResponseDto> subscribe(String username, SubscribeDto subscribeDto) {
        List<SiteName> subList = subscribeDto.getSiteNames();
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        List<SubscribeResponseDto> subResponseList = new ArrayList<>();

        for (SiteName s : subList) {
            if (subscriptionRepository.findByMemberAndSiteName(member, s).isEmpty()) {
                Subscription subscription = subscriptionRepository.save(subscribeDto.toEntity(member, s));
                SubscribeResponseDto subResponse = new SubscribeResponseDto(subscription);
                subResponseList.add(subResponse);
            }
        }

        return subResponseList;
    }

    @Transactional
    @Override
    public List<SubscribeResponseDto> showSubList(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("비정상 접근(no such user)"));

        List<Subscription> subList = subscriptionRepository.findAllByMember(member);
        List<SubscribeResponseDto> subResponseList = new ArrayList<>();

        for (Subscription se : subList) {
            SubscribeResponseDto subscribeResponseDto = new SubscribeResponseDto(se);
            subResponseList.add(subscribeResponseDto);
        }

        return subResponseList;
    }
}
