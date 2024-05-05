package com.example.snapEvent.subscribe.dto;

import com.example.snapEvent.subscribe.entity.Subscription;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class SubscribeResponseDto {
    private List<Subscription> subList;
}
