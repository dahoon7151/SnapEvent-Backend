package com.example.snapEvent.crawling.service;

import com.example.snapEvent.crawling.dto.InterParkDto;
import com.example.snapEvent.crawling.entity.InterPark;
import com.example.snapEvent.crawling.repository.InterParkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "Scheduler")
public class InterParkService {

    private final InterParkRepository interParkRepository;

    @Scheduled(cron = "0 0 2 * * *")
    public List<InterParkDto> getInterParkDatas() throws IOException {
        List<InterParkDto> interParkDtoList = new ArrayList<>();

        String url = "https://mticket.interpark.com/Notice/";
        Document docs = Jsoup.connect(url).get();
        Elements contents = docs.select("section .ticketRecomBox .item");
        List<Element> limitedContents = contents.subList(0, 10);

        for (Element content : limitedContents) {
            String alt = content.select("a img").attr("abs:alt");
            String title = alt.replace("https://mticket.interpark.com/Notice/", "");
            String date = content.select(".ticketOpen dd").text();

            InterPark interPark = InterPark.builder()
                    .title(title)
                    .image(resolveImageSrc(content.select("a img")))
                    .ticketOpenDate(date)
                    .href(content.select("a").attr("abs:href"))
                    .build();
            interParkRepository.save(interPark);
            interParkDtoList.add(new InterParkDto(interPark));
        }
        return interParkDtoList;
    }

    /*private LocalDateTime getLocalDate(Element content) {
        String dateText = content.select(".ticketOpen dd").text();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd (E) HH:mm");
        return LocalDateTime.parse(dateText, formatter);
    }*/

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
