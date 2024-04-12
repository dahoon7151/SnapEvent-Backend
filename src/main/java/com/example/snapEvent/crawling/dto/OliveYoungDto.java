package com.example.snapEvent.crawling.dto;

import com.example.snapEvent.crawling.embedded.DateRange;
import com.example.snapEvent.crawling.entity.OliveYoung;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor // Json -> Dto로 변환할 때 필요
@ToString
public class OliveYoungDto {

    private String title;
    private String description;
    private DateRange dateRange;
    private String image;
    private String href;

    public OliveYoungDto(OliveYoung oliveYoung) {
        this.title = oliveYoung.getTitle();
        this.description = oliveYoung.getDescription();
        this.dateRange = oliveYoung.getDateRange();
        this.image = oliveYoung.getImage();
        this.href = oliveYoung.getHref();
    }
}
