package com.example.snapEvent.crawling.cafe.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StarBucksDto {

    private String title;
    private String date;
    private String image;
    private String href;
}
