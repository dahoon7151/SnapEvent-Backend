package com.example.snapEvent.crawling.service;

import com.example.snapEvent.crawling.dto.OliveYoungDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OliveYoungService {
    // * URL 등록
    private final String url = "https://www.oliveyoung.co.kr/store/main/getEventList.do?evtType=20&pageIdx=1&t_page=%EC%9D%B4%EB%B2%A4%ED%8A%B8&t_click=%EB%AA%A8%EB%93%A0%ED%9A%8C%EC%9B%90";

    // * 크롤링해오는 응답속도 비용 낮추기 위해 미리 초기화
    @PostConstruct
    public List<OliveYoungDto> getOliveYoungDatas() throws IOException {
        List<OliveYoungDto> oliveYoungDtoList = new ArrayList<>();

        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("ul.event_thumb_list li");

        for (Element content : contents) {
            OliveYoungDto oliveYoungDto = OliveYoungDto.builder()
                    .image(content.select("a img").attr("data-original")) // src 절대주소
                    .date(content.select(".evt_date").text())
                    .description(content.select(".evt_desc").text())
                    .title(content.select(".evt_tit").text())
                    .build();

            oliveYoungDtoList.add(oliveYoungDto);
        }
        log.debug("title={}", oliveYoungDtoList.get(0).getTitle());
        log.debug("desc={}", oliveYoungDtoList.get(0).getDescription());
        log.debug("date={}", oliveYoungDtoList.get(0).getDate());
        log.debug("image={}", oliveYoungDtoList.get(0).getImage());
        return oliveYoungDtoList;
    }

}



