package com.cheese.ChikaChika.controller;

import com.cheese.ChikaChika.configuration.CustomWebSocketHandler;
import com.cheese.ChikaChika.consts.*;
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
            String brushedSection = source.getBrushedSection();

            String brushedLevel = teethBrushedService.getTeethSectionBrushedLevel(brushedSection);

            String nextBrushSection = brushedSection;
            if(brushedLevel == Consts.BRUSH_LEVEL.COMPLETE){
                nextBrushSection = teethBrushedService.getNextBrushSection(brushedSection);
            }

            String message = Message.getMessage(Message.Group.BRUSH_LEVEL, brushedLevel);
            if (nextBrushSection.equals("COMPLETE")) {
                message = Message.getMessage(Message.Group.COMPLETE_FLAG, Consts.COMPLETE_FLAG.Y);
            }
            else if(brushedLevel == Consts.BRUSH_LEVEL.COMPLETE){
                message += "\n" + Message.getMessage(Message.Group.NEXT_BRUSH_SECTION, nextBrushSection);
            }

            response = new Teeth(ResponseStatus.SUCCESS.getLevel(), message,
                    brushedSection, brushedLevel, nextBrushSection);
        } else {
            response = new Teeth(ResponseStatus.ERROR.getLevel(), Message.getMessage(Message.Group.ERROR, Consts.ERROR_CODE.NOT_FOLLOW_ORDER));
        }

        if (webSocket.sendMessage(response)) {
            log.info("Send message success!");
        } else {
            log.info("Connection with client has not been established...");
        }
    }
}
