package com.cheese.ChikaChika.consts;

public enum BrushedLevel {

    LITTLE(3),
    MEDIUM(7),
    COMPLETE(10);

    private int level;

    private BrushedLevel(){}

    private BrushedLevel(int level){
        this.level = level;
    }

    public int getLevel(){
        return level;
    }
}
