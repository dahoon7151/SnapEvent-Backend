package com.example.snapEvent.subscribe.dto;

import com.example.snapEvent.member.entity.Member;
import com.example.snapEvent.subscribe.SiteName;
import com.example.snapEvent.subscribe.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscribeDto {
    private List<SiteName> siteNames;

    public Subscription toEntity(Member member, SiteName siteName) {
        return Subscription.builder()
                .member(member)
                .siteName(siteName)
                .build();
    }
}
