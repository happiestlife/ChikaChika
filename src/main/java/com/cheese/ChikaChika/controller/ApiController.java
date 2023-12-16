package com.cheese.ChikaChika.controller;

import com.cheese.ChikaChika.configuration.CustomWebSocketHandler;
import com.cheese.ChikaChika.consts.ResponseStatus;
import com.cheese.ChikaChika.model.Teeth;
import com.cheese.ChikaChika.model.TeethSource;
import com.cheese.ChikaChika.service.TeethBrushedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/reset")
    @ResponseBody
    public void resetBrushedInfo(){
        teethBrushedService.resetBrushedInfo();
    }

    @PostMapping("/api/teeth")
    @ResponseBody
    public void setTeethBrushedInformation(@RequestBody TeethSource source) {
        // 1. 세션에 각 부위에 대한 양치 횟수 저장
        boolean saveSuccess = teethBrushedService.setTeethSectionBrushedLevel(source);

        // 2. 해당 데이터를 웹에 업데이트
        Teeth response;
        if (saveSuccess) {
            int brushedLevel = teethBrushedService.getTeethSectionBrushedLevel(source.getBrushedSection());

            response = new Teeth(ResponseStatus.SUCCESS.getLevel(), source.getBrushedSection(), brushedLevel);
        } else {
            response = new Teeth(ResponseStatus.ERROR.getLevel(), "올바른 동작으로 닦아주세요.");
        }

        if (webSocket.sendMessage(response)) {
            log.info("Send message success!");
        } else {
            log.info("Connection with client has not been established...");
        }
    }
}
