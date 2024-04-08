package com.example.snapEvent.crawling.cafe.controller;

import com.example.snapEvent.crawling.cafe.dto.EdiyaDto;
import com.example.snapEvent.crawling.cafe.dto.HollysDto;
import com.example.snapEvent.crawling.cafe.dto.LotteEatzDto;
import com.example.snapEvent.crawling.cafe.dto.StarBucksDto;
import com.example.snapEvent.crawling.cafe.service.EdiyaService;
import com.example.snapEvent.crawling.cafe.service.HollysService;
import com.example.snapEvent.crawling.cafe.service.LotteEatzService;
import com.example.snapEvent.crawling.cafe.service.StarBucksService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CafeController {

    private final HollysService hollysService;
    private final EdiyaService ediyaService;
    private final LotteEatzService lotteEatzService;
    private final StarBucksService starBucksService;

    @GetMapping("/crawling/hollys-coffee")
    public String hollys(Model model) throws IOException {
        List<HollysDto> hollysList = hollysService.getHollysDatas();
        model.addAttribute("hollys", hollysList);

        return "hollys";
    }

    @GetMapping("/crawling/ediya-coffee")
    public String ediya(Model model) throws IOException {
        List<EdiyaDto> ediyaList = ediyaService.getEdiyaDatas();
        model.addAttribute("ediya", ediyaList);

        return "ediya";
    }

    @GetMapping("/crawling/lotte-eatz")
    public String eatz(Model model) {
        List<LotteEatzDto> lotteEatzDtoList = lotteEatzService.getLotteEatzDatas();
        model.addAttribute("lotteEatz", lotteEatzDtoList);

        return "lotteEatz";
    }

    @GetMapping("crawling/starbucks")
    public String starbucks(Model model) {
        List<StarBucksDto> starBucksDtoList = starBucksService.getStarBucksDatas();
        model.addAttribute("starbucks", starBucksDtoList);

        return "starbucks";
    }
}
