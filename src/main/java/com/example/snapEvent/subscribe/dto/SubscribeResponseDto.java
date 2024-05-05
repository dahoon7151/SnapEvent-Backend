package com.example.snapEvent.subscribe.dto;

import com.example.snapEvent.subscribe.SiteName;
import com.example.snapEvent.subscribe.entity.Subscription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeResponseDto {
    private Long id;
    private SiteName subedSiteName;

    public SubscribeResponseDto(Subscription subscription) {
        this.id = subscription.getId();
        this.subedSiteName = subscription.getSiteName();
    }
}
