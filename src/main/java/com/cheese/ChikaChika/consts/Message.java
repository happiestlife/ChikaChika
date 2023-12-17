package com.cheese.ChikaChika.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * DB 대체
 */
public class Message {

    public static class Group{
        public static final String BRUSH_LEVEL = "BRUSH_LEVEL";
        public static final String COMPLETE_FLAG = "COMPLETE_FLAG";
        public static final String ERROR = "ERROR";
    }

    private static final Map<String, String> container = new HashMap<>(){{
        // brush level
        put(String.format("%s_%s", Group.BRUSH_LEVEL, Consts.BRUSH_LEVEL.LITTLE),   "잘하고 있어요! 조금만 더 해볼까요?");
        put(String.format("%s_%s", Group.BRUSH_LEVEL, Consts.BRUSH_LEVEL.MEDIUM),   "거의 다 됐어!");
        put(String.format("%s_%s", Group.BRUSH_LEVEL, Consts.BRUSH_LEVEL.COMPLETE), "다 됐어요! 이제 다음으로 넘어가볼까요?");

        // complete flag
        put(String.format("%s_%s", Group.COMPLETE_FLAG, Consts.COMPLETE_FLAG.Y), "잘했어요! 모두 완료했어요!");

        // error msg
        put(String.format("%s_%s", Group.ERROR, Consts.ERROR_CODE.NOT_FOLLOW_ORDER), "올바른 순서로 이빨을 닦아주세요.");
    }};

    public static String getMessage(String group, String code){
        return container.get(group + "_" + code);
    }
}
