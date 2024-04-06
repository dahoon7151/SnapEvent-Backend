package com.example.snapEvent.crawling.controller;

import com.example.snapEvent.crawling.dto.InterParkDto;
import com.example.snapEvent.crawling.service.InterParkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class InterParkController {

    private final InterParkService interParkService;

    @GetMapping("/crawling/interpark")
    public String interpark(Model model) throws IOException {
        List<InterParkDto> interParkList = interParkService.getInterParkDatas();
        model.addAttribute("interpark", interParkList);
        return "interpark";
    }
}
