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
        public static final String NEXT_BRUSH_SECTION = "NEXT_BRUSH_SECTION";
    }

    private static final Map<String, String> container = new HashMap<>(){{
        // brush level
        put(String.format("%s_%s", Group.BRUSH_LEVEL, Consts.BRUSH_LEVEL.LITTLE),   "잘하고 있어요! 조금만 더 해볼까요?");
        put(String.format("%s_%s", Group.BRUSH_LEVEL, Consts.BRUSH_LEVEL.MEDIUM),   "거의 다 됐어!");
        put(String.format("%s_%s", Group.BRUSH_LEVEL, Consts.BRUSH_LEVEL.COMPLETE), "다 됐어요! 이제 다음으로 넘어가볼까요?");

        // next brush section
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UF_OUTSIDE), "네모부분의 바깥쪽(보이는 쪽)을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UF_INSIDE),  "네모부분의 안쪽(안보이는 쪽)을 양치해볼까요?");

        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UR_OUTSIDE), "네모부분의 바깥쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UR_INSIDE),  "네모부분의 안쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UR_ABOVE),   "네모부분의 위쪽을 양치해볼까요?");

        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UL_OUTSIDE), "네모부분의 바깥쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UL_INSIDE),  "네모부분의 안쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.UL_ABOVE),   "네모부분의 위쪽을 양치해볼까요?");

        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LF_OUTSIDE), "네모부분의 바깥쪽(보이는 쪽)을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LF_INSIDE),  "네모부분의 안쪽(안보이는 쪽)을 양치해볼까요?");

        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LR_OUTSIDE), "네모부분의 바깥쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LR_INSIDE),  "네모부분의 안쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LR_ABOVE),   "네모부분의 위쪽을 양치해볼까요?");

        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LL_OUTSIDE), "네모부분의 바깥쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LL_INSIDE),  "네모부분의 안쪽을 양치해볼까요?");
        put(String.format("%s_%s", Group.NEXT_BRUSH_SECTION, Consts.BRUSH_SECTION.LL_ABOVE),   "네모부분의 위쪽을 양치해볼까요?");

        // complete flag
        put(String.format("%s_%s", Group.COMPLETE_FLAG, Consts.COMPLETE_FLAG.Y), "잘했어요! 모두 완료했어요!");

        // error
        put(String.format("%s_%s", Group.ERROR, Consts.ERROR_CODE.NOT_FOLLOW_ORDER), "올바른 순서로 이빨을 닦아주세요.");
    }};

    public static String getMessage(String group, String code){
        return container.get(group + "_" + code);
    }
}
