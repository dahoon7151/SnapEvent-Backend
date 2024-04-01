package com.example.snapEvent.crawling.service;

import com.example.snapEvent.crawling.dto.SsfDto;
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
public class SsfService {

    public List<SsfDto> getSsfDatas() throws IOException {
        List<SsfDto> ssfDtoList = new ArrayList<>();

        String url = "https://www.ssfshop.com/event/list";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("div.list-area ul.ssf-events li");

        for (Element content : contents) {
            SsfDto ssfDto = SsfDto.builder()
                    .title(content.select(".tit").text())
                    .description(content.select(".desc").text())
                    .date(content.select(".date").text())
                    .image(resolveImageSrc(content.select("a img")))
                    .href(content.select("a").attr("abs:href"))
                    .build();
            ssfDtoList.add(ssfDto);
        }
        return ssfDtoList;
    }

    // img attr이 달라서 따로 메서드로 해결
    private String resolveImageSrc(Elements elements) {
        String imageSrc = elements.attr("abs:src");
        if (imageSrc.isEmpty()) {
            imageSrc = elements.attr("data-original");
        }
        return imageSrc;
    }
}
