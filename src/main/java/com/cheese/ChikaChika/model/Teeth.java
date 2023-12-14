package com.cheese.ChikaChika.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Teeth implements Serializable {
    private final int[] teethSectionBrushedCnt;
}
