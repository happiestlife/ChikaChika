package com.cheese.ChikaChika.model;

import lombok.*;

import java.io.Serializable;

@Getter @Setter
public class Teeth implements Serializable {
    private String status;
    private String msg;
    private String brushedSection;
    private String brushedLevel;
    private String nextBrushSection;

    public Teeth(String status, String msg, String brushedSection, String brushedLevel, String nextBrushSection) {
        this.status = status;
        this.msg = msg;
        this.brushedSection = brushedSection;
        this.brushedLevel = brushedLevel;
        this.nextBrushSection = nextBrushSection;
    }

    public Teeth (String status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
