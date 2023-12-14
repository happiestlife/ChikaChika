package com.cheese.ChikaChika.util;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 한면의 유저를 상대로 작동하는다는 설계를 전재
 */
@Component
public class TeethSessionManager {

    private final String BRUSHED_KEY = "brushed";

    private final String CAVITY_KEY = "cavity";

    private final Map<String, Object> teethInfo = new ConcurrentHashMap<>();

    public void setBrushedCnt(int[] isTeethSectionBrushed){
        teethInfo.put(BRUSHED_KEY, isTeethSectionBrushed);
    }

    public int[] getBrushedCnt(){
        return (int[]) teethInfo.get(BRUSHED_KEY);
    }

    public void setTeethCavity(boolean[] isTeethSectionHasCavity){
        teethInfo.put(CAVITY_KEY, isTeethSectionHasCavity);
    }

    public boolean[] getTeethCavity(){
        return (boolean[]) teethInfo.get(CAVITY_KEY);
    }

}
