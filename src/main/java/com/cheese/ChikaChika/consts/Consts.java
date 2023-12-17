package com.cheese.ChikaChika.consts;

public class Consts {
    public interface BRUSH_LEVEL {
        String NONE = "NONE";
        String LITTLE = "LITTLE";
        String MEDIUM = "MEDIUM";
        String COMPLETE = "COMPLETE";
    }

    public interface COMPLETE_FLAG {
        String Y = "Y";
        String N = "N";
    }

    public interface ERROR_CODE {
        String NOT_FOLLOW_ORDER = "NOT_FOLLOW_ORDER";
    }

    public interface BRUSH_SECTION {
        String UF_OUTSIDE = "uf_outside";
        String UF_INSIDE  = "uf_inside";

        String UR_OUTSIDE = "ur_outside";
        String UR_INSIDE  = "ur_inside";
        String UR_ABOVE   = "ur_above";

        String UL_OUTSIDE = "ul_outside";
        String UL_INSIDE  = "ul_inside";
        String UL_ABOVE   = "ul_above";

        String LF_OUTSIDE = "lf_outside";
        String LF_INSIDE  = "lf_inside";

        String LR_OUTSIDE = "lr_outside";
        String LR_INSIDE  = "lr_inside";
        String LR_ABOVE   = "lr_above";

        String LL_OUTSIDE = "ll_outside";
        String LL_INSIDE  = "ll_inside";
        String LL_ABOVE   = "ll_above";
    }
}
