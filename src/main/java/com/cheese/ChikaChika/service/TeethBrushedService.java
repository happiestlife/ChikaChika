package com.cheese.ChikaChika.service;

import com.cheese.ChikaChika.consts.BrushedLevel;
import com.cheese.ChikaChika.consts.WebConsts;
import com.cheese.ChikaChika.model.Teeth;
import com.cheese.ChikaChika.model.TeethSource;
import com.cheese.ChikaChika.util.TeethSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeethBrushedService {

    private final TeethSessionManager teethSession;


    public int[] getTeethSectionBrushedLevel(){
        final int littleLevel = BrushedLevel.LITTLE.getLevel();
        final int mediumLevel = BrushedLevel.MEDIUM.getLevel();
        final int completeLevel = BrushedLevel.COMPLETE.getLevel();

        int[] brushedCnt = teethSession.getBrushedCnt();
        int[] webBrushedLevel = new int[brushedCnt.length];
        for (int i = 0; i < webBrushedLevel.length; i++) {
            if(brushedCnt[i] < littleLevel){
                webBrushedLevel[i] = WebConsts.BRUSHED_LEVEL.NONE;
            } else if (littleLevel <= brushedCnt[i] && brushedCnt[i] < mediumLevel) {
                webBrushedLevel[i] = WebConsts.BRUSHED_LEVEL.LITTLE;
            } else if (mediumLevel <= brushedCnt[i] && brushedCnt[i] < completeLevel) {
                webBrushedLevel[i] = WebConsts.BRUSHED_LEVEL.MEDIUM;
            } else {
                webBrushedLevel[i] = WebConsts.BRUSHED_LEVEL.COMPLETE;
            }
        }

        return webBrushedLevel;
    }

    public void setTeethSectionBrushedLevel(TeethSource brushedTeethSectionCnt){
        int[] teethSectionBrushedCnt = teethSession.getBrushedCnt();
        if(teethSectionBrushedCnt == null){
            teethSectionBrushedCnt = new int[brushedTeethSectionCnt.getTeethBrushedSectionCnt().length];
        }

        int[] sourceTeethBrushedSectionCnt = brushedTeethSectionCnt.getTeethBrushedSectionCnt();
        for (int i = 0; i < teethSectionBrushedCnt.length; i++) {
            teethSectionBrushedCnt[i] += sourceTeethBrushedSectionCnt[i];
        }

        teethSession.setBrushedCnt(teethSectionBrushedCnt);
    }
}
