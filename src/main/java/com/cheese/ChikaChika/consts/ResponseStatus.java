package com.cheese.ChikaChika.consts;

public enum ResponseStatus {
    SUCCESS("S"),
    ERROR("E");

    private String level;

    private ResponseStatus(){}

    private ResponseStatus(String level){
        this.level = level;
    }

    public String getLevel(){
        return level;
    }
}
