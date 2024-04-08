package com.example.snapEvent.crawling.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor // Json -> Dto로 변환할 때 필요
@ToString
public class OliveYoungDto {
    private String title;
    private String description;
    private String date;
    private String image;
    private String href;
}
