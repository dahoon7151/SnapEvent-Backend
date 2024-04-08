package com.example.snapEvent.crawling.service;

import com.example.snapEvent.crawling.SeleniumHelper;
import com.example.snapEvent.crawling.dto.OliveYoungDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OliveYoungService {

    public List<OliveYoungDto> getOliveYoungDatas() throws IOException {
        List<OliveYoungDto> oliveYoungDtoList = new ArrayList<>();

        String url = "https://www.oliveyoung.co.kr/store/main/getEventList.do?t_page=%EB%9E%AD%ED%82%B9&t_click=GNB&t_gnb_type=%EC%9D%B4%EB%B2%A4%ED%8A%B8&t_swiping_type=N";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("ul.event_thumb_list li");

        for (Element content : contents) {
            String title = content.select(".evt_tit").text();
            String date = content.select(".evt_date").text();
            String image = content.select("a img").attr("abs:data-original");
            String description = content.select(".evt_desc").text();

            String href = extractHref(content);

            OliveYoungDto oliveYoungDto = OliveYoungDto.builder()
                    .title(title)
                    .date(date)
                    .image(image)
                    .description(description)
                    .href(href)
                    .build();
            oliveYoungDtoList.add(oliveYoungDto);
            log.debug("oliveYoungDto={}", oliveYoungDto);
        }

        return oliveYoungDtoList;
    }

    private String extractHref(Element content) {
        Element inputElement = content.selectFirst("input[name=urlInfo]");
        assert inputElement != null;
        String urlInfoValue = inputElement.attr("value");
        String baseUrl = "https://www.oliveyoung.co.kr/store/";

        return baseUrl.concat(urlInfoValue);
    }

}




