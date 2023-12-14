package com.cheese.ChikaChika.model;

import lombok.Data;

@Data
public class TeethSource {

    public final static int SECTION_CNT = 16;

    private final int[] teethBrushedSectionCnt;
}
