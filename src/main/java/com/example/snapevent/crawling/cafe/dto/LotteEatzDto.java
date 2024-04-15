package com.example.snapevent.crawling.cafe.dto;

import com.example.snapevent.crawling.cafe.entity.LotteEatz;
import com.example.snapevent.crawling.embedded.DateRange;
import lombok.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LotteEatzDto {

    private String title;
    private DateRange dateRange;
    private String image;
    private String href;
    private String field; // 카테고리 분류

    public LotteEatzDto(LotteEatz lotteEatz) {
        this.title = lotteEatz.getTitle();
        this.dateRange = lotteEatz.getDateRange();
        this.image = lotteEatz.getImage();
        this.href = lotteEatz.getHref();
        this.field = lotteEatz.getField();
    }
}
