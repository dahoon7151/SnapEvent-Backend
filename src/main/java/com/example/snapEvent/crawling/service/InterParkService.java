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
@Slf4j
public class InterParkService {

    private final InterParkRepository interParkRepository;

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

            if (isTitleExist(title)) {
                log.debug("Skipping duplicate title: {}", title);
                InterPark existingInterPark = interParkRepository.findByTitle(title);
                if (existingInterPark != null) {
                    interParkDtoList.add(new InterParkDto(existingInterPark));
                }
                continue;
            }

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

    private boolean isTitleExist(String title) {
        return interParkRepository.existsByTitle(title);
    }

    //? 나중에 정렬 기능같은거 있으면 추가할 예정
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
