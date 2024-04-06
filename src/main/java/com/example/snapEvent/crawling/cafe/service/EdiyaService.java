package com.example.snapEvent.crawling.cafe.service;

import com.example.snapEvent.crawling.cafe.dto.EdiyaDto;
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
public class EdiyaService {

    public List<EdiyaDto> getEdiyaDatas() throws IOException {

        List<EdiyaDto> ediyaDtoList = new ArrayList<>();

        String url = "https://ediya.com/contents/event.html?tb_name=event";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select(".board_e li");

        for (Element content : contents) {

            // 종료 이벤트를 만나면 break
            if (!content.select(".end").isEmpty()) {
                break;
            }

            // (date)span 태그 제거
            Element eventDateElements = content.select("dd").first();

            assert eventDateElements != null;
            String dateExcludingSpan = eventDateElements.ownText();

            EdiyaDto ediyaDto = EdiyaDto.builder()
                    .title(content.select(".board_e_con a").text())
                    .date(dateExcludingSpan)
                    .image(resolveImageSrc(content.select("a img")))
                    .build();
            ediyaDtoList.add(ediyaDto);
        }
        return ediyaDtoList;
    }

    private String resolveImageSrc(Elements elements) {
        String imageSrc = elements.attr("abs:src");
        if (imageSrc.isEmpty()) {
            imageSrc = elements.attr("data-original");
        }
        return imageSrc;
    }

}
