package com.example.snapEvent.subscribe.dto;

import com.example.snapEvent.subscribe.SiteName;
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
}
