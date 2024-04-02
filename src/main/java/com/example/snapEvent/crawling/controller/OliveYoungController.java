package com.example.snapEvent.crawling.controller;

import com.example.snapEvent.crawling.dto.OliveYoungDto;
import com.example.snapEvent.crawling.service.OliveYoungService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OliveYoungController {

    private final OliveYoungService oliveYoungService;

    @GetMapping("/crawling/olive-young")
    public String oliveYoung(Model model) throws IOException {
        List<OliveYoungDto> oliveYoungList = oliveYoungService.getOliveYoungDatas();
        model.addAttribute("oliveYoung", oliveYoungList);

        return "oliveYoung";
    }
}
