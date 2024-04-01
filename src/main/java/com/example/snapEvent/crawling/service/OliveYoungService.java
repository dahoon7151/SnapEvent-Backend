package com.example.snapEvent.crawling.service;

import com.example.snapEvent.crawling.dto.OliveYoungDto;
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

    // * @PostConstruct 어노테이션 지움(크롤링할 데이터가 많으면 리소스 그만큼 소비) -> 없어도 정상적으로 실행됨
    public List<OliveYoungDto> getOliveYoungDatas() throws IOException {
        List<OliveYoungDto> oliveYoungDtoList = new ArrayList<>();

        // * URL 등록
        String url = "https://www.oliveyoung.co.kr/store/main/getEventList.do?evtType=20&pageIdx=1&t_page=%EC%9D%B4%EB%B2%A4%ED%8A%B8&t_click=%EB%AA%A8%EB%93%A0%ED%9A%8C%EC%9B%90";
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

        return oliveYoungDtoList;
    }

}



