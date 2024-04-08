package com.example.snapEvent.crawling.service;

import com.example.snapEvent.crawling.dto.InterParkDto;
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
public class InterParkService {

    public List<InterParkDto> getInterParkDatas() throws IOException {
        List<InterParkDto> interParkDtoList = new ArrayList<>();

        String url = "https://mticket.interpark.com/Notice/";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("section .ticketRecomBox .item");

        for (Element content : contents) {
            String alt = content.select("a img").attr("abs:alt");
            String title = alt.replace("https://mticket.interpark.com/Notice/", "");

            InterParkDto interParkDto = InterParkDto.builder()
                    .title(title)
                    .image(resolveImageSrc(content.select("a img")))
                    .ticketOpenDate(content.select(".ticketOpen dd").text())
                    .href(content.select("a").attr("abs:href"))
                    .build();
            interParkDtoList.add(interParkDto);
            log.debug("interParkDto={}", interParkDto);
        }
        return interParkDtoList;
    }

    private String resolveImageSrc(Elements elements) {
        // jpg 파일 형식을 우선적으로 추출
        String onerrorSrc = elements.attr("abs:onerror");
        String replaced = onerrorSrc.replace("https://mticket.interpark.com/Notice/this.src=", "");
        String cleanedReplaced = replaced.replace("'", "");
        // jpg 파일이 없어서 NoImage.gif 파일을 추출한다면
        if (onerrorSrc.contains("NoImage.gif")) {
            return elements.attr("abs:src"); // src로 대체
        }
        return cleanedReplaced;
    }
}
