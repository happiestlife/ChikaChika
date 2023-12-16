package com.cheese.ChikaChika.model;

import com.cheese.ChikaChika.consts.ResponseStatus;
import lombok.*;
import org.apache.coyote.Response;

import java.io.Serializable;

@Getter @Setter
public class Teeth implements Serializable {
    private String status;
    private String errorMsg;
    private String brushedSection;
    private int brushedLevel;

    public Teeth (String status, String brushedSection, int brushedLevel) {
        this.status = status;
        this.brushedSection = brushedSection;
        this.brushedLevel = brushedLevel;
    }

    public Teeth (String status, String errorMsg) {
        this.status = status;
        this.errorMsg = errorMsg;
    }
}
