package com.example.snapEvent.crawling.cafe.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LotteEatzDto {

    private String title;
    private String date;
    private String image;
    private String href;
    private String field; // 카테고리 분류
}
