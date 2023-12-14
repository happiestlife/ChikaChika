package com.cheese.ChikaChika.controller;

import com.cheese.ChikaChika.model.Teeth;
import com.cheese.ChikaChika.util.TeethSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class ApiController {

    private final TeethSessionManager teethSession;

    @GetMapping(value = {"/", "/home"})
    public String getHomePage() {
        return "homepage";
    }

    @GetMapping("/api/teeth")
    @ResponseBody
    public Teeth getTeethBrushedInformation() {
        // 1. 세션 정보에서 이빨 닦이 정도를 나타내는 데이터 불러오기
        boolean[] brushedTeethSections = teethSession.getBrushedTeethInfo();

        // 2. teeth 객체에 담아 전달.
        return new Teeth(brushedTeethSections);
    }

    @PostMapping("/api/teeth")
    public void setTeethBrushedInformation(String brushedTeethInfo) {
        boolean[] isTeethSectionsBrushed = teethSession.getBrushedTeethInfo();
        if(isTeethSectionsBrushed == null){
            isTeethSectionsBrushed = new boolean[brushedTeethInfo.length()];
        }

        for (int i = 0; i < brushedTeethInfo.length(); i++) {
            // 이미 닦인 부위에 대해서는 처리 x
            if(isTeethSectionsBrushed[i]) continue;

            // 현재 닦인 부분에 대해서 처리리
            char iSectionBrushed = brushedTeethInfo.charAt(i);
            if(iSectionBrushed == '1'){
                isTeethSectionsBrushed[i] = true;
            }
        }

        teethSession.setBrushedTeethInfo(isTeethSectionsBrushed);
    }
}
