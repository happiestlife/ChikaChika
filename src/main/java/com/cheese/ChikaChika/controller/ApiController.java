package com.cheese.ChikaChika.controller;

import com.cheese.ChikaChika.configuration.CustomWebSocketHandler;
import com.cheese.ChikaChika.model.Teeth;
import com.cheese.ChikaChika.model.TeethSource;
import com.cheese.ChikaChika.service.TeethBrushedService;
import com.cheese.ChikaChika.util.TeethSessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ApiController {

    private final TeethBrushedService teethBrushedService;

    private final CustomWebSocketHandler webSocket;

    @GetMapping(value = {"/", "/home"})
    public String getHomePage() {
        return "homepage";
    }

    @GetMapping("/api/teeth")
    @ResponseBody
    public Teeth getTeethBrushedInformation() {
        // 1. 세션 정보에서 이빨 닦이 정도를 나타내는 데이터 불러오기
        int[] brushedTeethSections = teethBrushedService.getTeethSectionBrushedLevel();

        // 2. teeth 객체에 담아 전달.
        return new Teeth(brushedTeethSections);
    }

    @PostMapping("/api/teeth")
    public void setTeethBrushedInformation(@RequestBody TeethSource brushedTeethSectionCnt) {
        // 1. 세션에 각 부위에 대한 양치 횟수 저장
        teethBrushedService.setTeethSectionBrushedLevel(brushedTeethSectionCnt);

        // 2. 해당 데이터를 웹에 업데이트
        if(webSocket.sendMessage(new Teeth(teethBrushedService.getTeethSectionBrushedLevel()))){
            log.info("Send message success!");
        }
        else{
            log.info("Connection with client has not been established...");
        }
    }
}
