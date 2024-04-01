package com.example.snapEvent.crawling.controller;

import com.example.snapEvent.crawling.dto.SsfDto;
import com.example.snapEvent.crawling.service.SsfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SsfController {

    private final SsfService ssfService;

    @GetMapping("/crawling/ssfShop")
    public String ssf(Model model) throws IOException {
        List<SsfDto> ssfList = ssfService.getSsfDatas();
        model.addAttribute("ssf", ssfList);

        return "ssf";
    }
}
