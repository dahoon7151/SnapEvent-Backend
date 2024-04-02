package com.example.snapEvent.crawling.cafe.service;

import com.example.snapEvent.crawling.cafe.dto.HollysDto;
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
public class HollysService {

    public List<HollysDto> getHollysDatas() throws IOException {
        List<HollysDto> hollysDtoList = new ArrayList<>();

        String url = "https://www.hollys.co.kr/news/event/list.do?pageNo=1&closeYn=&division=";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("div.event_list_wr .event_listBox");

        for (Element content : contents) {

            // a.event_finish를 만나면 반복문 종료
            if (!content.select("a.event_finish").isEmpty()) {
                break;
            }

            // (date)span 태그 제거
            Element eventDateElements = content.select(".event_date").first();

            assert eventDateElements != null;
            String dateExcludingSpan = eventDateElements.ownText();

            // (title)sort 클래스 제거
            Element dtElement = content.select("dl.event_list dt").first();
            assert dtElement != null;
            String titleExcludingSort = dtElement.select("a").text();

            HollysDto hollysDto = HollysDto.builder()
                    .title(titleExcludingSort)
                    .image(resolveImageSrc(content.select("a img")))
                    .date(dateExcludingSpan)
                    .build();
            hollysDtoList.add(hollysDto);
        }
        return hollysDtoList;
    }

    private String resolveImageSrc(Elements elements) {
        String imageSrc = elements.attr("abs:src");
        if (imageSrc.isEmpty()) {
            imageSrc = elements.attr("data-original");
        }
        return imageSrc;
    }
}