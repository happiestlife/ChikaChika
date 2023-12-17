package com.cheese.ChikaChika.service;

import com.cheese.ChikaChika.consts.BrushedLevel;
import com.cheese.ChikaChika.consts.Consts;
import com.cheese.ChikaChika.model.TeethSource;
import com.cheese.ChikaChika.util.TeethSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeethBrushedService {

    private final String[] BRUSH_ORDER = {"uf_outside", "uf_inside",
            "ur_outside", "ur_inside", "ur_above",
            "ul_outside", "ul_inside", "ul_above",
            "lf_outside", "lf_inside",
            "lr_outside", "lr_inside", "lr_above",
            "ll_outside", "ll_inside", "ll_above"};

    private final TeethSessionManager teethSession;


    /**
     * 현재 session에 저장되어 있던 각 이빨 구역의 닦임 횟수에 따라 UI로 어떻게 표현될 지에 대해서 코드 반환
     * @return WebConsts.BRUSHED_LEVEL
     */
    public String[] getTeethSectionBrushedLevel(){
        final int littleLevel = BrushedLevel.LITTLE.getLevel();
        final int mediumLevel = BrushedLevel.MEDIUM.getLevel();
        final int completeLevel = BrushedLevel.COMPLETE.getLevel();

        int[] brushedCnt = teethSession.getBrushedCnt();
        String[] webBrushedLevel = new String[brushedCnt.length];
        for (int i = 0; i < webBrushedLevel.length; i++) {
            if(brushedCnt[i] < littleLevel){
                webBrushedLevel[i] = Consts.BRUSH_LEVEL.NONE;
            } else if (littleLevel <= brushedCnt[i] && brushedCnt[i] < mediumLevel) {
                webBrushedLevel[i] = Consts.BRUSH_LEVEL.LITTLE;
            } else if (mediumLevel <= brushedCnt[i] && brushedCnt[i] < completeLevel) {
                webBrushedLevel[i] = Consts.BRUSH_LEVEL.MEDIUM;
            } else {
                webBrushedLevel[i] = Consts.BRUSH_LEVEL.COMPLETE;
            }
        }

        return webBrushedLevel;
    }

    public String getTeethSectionBrushedLevel(String section){
        final int littleLevel = BrushedLevel.LITTLE.getLevel();
        final int mediumLevel = BrushedLevel.MEDIUM.getLevel();
        final int completeLevel = BrushedLevel.COMPLETE.getLevel();

        int order = getSectionBrushOrderIdx(section);
        int brushedCnt = teethSession.getBrushedCnt()[order];
        if(brushedCnt < littleLevel){
            return Consts.BRUSH_LEVEL.NONE;
        }
        else if (littleLevel <= brushedCnt && brushedCnt < mediumLevel) {
            return Consts.BRUSH_LEVEL.LITTLE;
        }
        else if (mediumLevel <= brushedCnt && brushedCnt < completeLevel) {
            return Consts.BRUSH_LEVEL.MEDIUM;
        }
        else {
            return Consts.BRUSH_LEVEL.COMPLETE;
        }
    }

    /**
     * 기존에 있던 각 이빨 구역의 닦임 횟수에 현재 인자로 전달된 구역에 대한 닦인 횟수를 더함
     * @param brushedTeethSectionCnt
     * @return
     *  true  -> 이전에 닦고 있던 부위가 덜 닦였는데 현재 닦은 부위가 같은 부위일 때 / 새로운 부위를 닦아야 할 경우.
     *  false -> 이전에 닦고 있던 부위가 덜 닦였는데 현재 닦은 부위가 다른 부위일 때.
     */
    public boolean setTeethSectionBrushedLevel(TeethSource brushedTeethSectionCnt){
        int[] teethSectionBrushedCnt = teethSession.getBrushedCnt();
        if(teethSectionBrushedCnt == null){
            teethSectionBrushedCnt = new int[BRUSH_ORDER.length];
        }

        int curBrushedSectionIdx = getSectionBrushOrderIdx(brushedTeethSectionCnt.getBrushedSection());
        int curBrushedSectionCnt = brushedTeethSectionCnt.getBrushedCnt();
        int needToBrushSectionIdx = getNeedToBrushSectionIdx();

        // 모두 닦았을 경우
        if (needToBrushSectionIdx >= BRUSH_ORDER.length) {
            return true;
        }
        // 순서에 따라 현재 닦고 있는 / 닦아야 할 부위와 닦은 부위가 다를 경우
        else if (curBrushedSectionIdx > needToBrushSectionIdx) {
            return false;
        }

        teethSectionBrushedCnt[curBrushedSectionIdx] += curBrushedSectionCnt;
        teethSession.setBrushedCnt(teethSectionBrushedCnt);

        return true;
    }

    /**
     * 0보다 크고 Complete 횟수보다 작은만큼 닦인 부위가 있는지 반환
     * @return {int} 있으면 해당 구역의 index, 없으면 다음에 닦아야 할 부위에 대한 index 반환
     */
    public int getNeedToBrushSectionIdx() {
        int[] teethSectionBrushedCnt = teethSession.getBrushedCnt();
        if(teethSectionBrushedCnt == null){
            teethSectionBrushedCnt = new int[BRUSH_ORDER.length];
        }

        int lastBrushCompletedIdx = -1;
        for (int i = 0; i < teethSectionBrushedCnt.length; i++) {
            if (teethSectionBrushedCnt[i] >= BrushedLevel.COMPLETE.getLevel()) {
                lastBrushCompletedIdx = i;
            }
            else if (0 < teethSectionBrushedCnt[i] && teethSectionBrushedCnt[i] < BrushedLevel.COMPLETE.getLevel()) {
                return i;
            }
        }

        return lastBrushCompletedIdx + 1;
    }

    public void resetBrushedInfo(){
        teethSession.resetBrushedCnt();
    }

    /**
     * 다음에 닦아야 할 section의 명칭 반환
     * @param section
     * @return 다음 section의 명칭, 인자로 전달된 section이 마지막 section이었다면 COMPLETE 반환
     */
    public String getNextBrushSection(String section){
        int idx = getSectionBrushOrderIdx(section);
        if(idx == BRUSH_ORDER.length - 1){
            return "COMPLETE";
        }

        return BRUSH_ORDER[idx + 1];
    }

    private int getSectionBrushOrderIdx(String section){
        for (int i = 0; i < BRUSH_ORDER.length; i++) {
            if (BRUSH_ORDER[i].equals(section)) {
                return i;
            }
        }

        return -1;
    }
}
