package com.example.snapEvent.crawling.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SsfDto {
    private String title;
    private String description;
    private String date;
    private String image;
    private String href;
}
