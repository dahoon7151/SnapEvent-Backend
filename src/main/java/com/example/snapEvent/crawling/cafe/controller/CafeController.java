package com.example.snapEvent.crawling.cafe.controller;

import com.example.snapEvent.crawling.cafe.dto.HollysDto;
import com.example.snapEvent.crawling.cafe.service.HollysService;
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

    @GetMapping("crawling/hollys-coffee")
    public String hollys(Model model) throws IOException {
        List<HollysDto> hollysList = hollysService.getHollysDatas();
        model.addAttribute("hollys", hollysList);

        return "hollys";
    }
}
