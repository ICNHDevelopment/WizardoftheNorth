package com.icnhdevelopment.wotn.players;

/**
 * Created by kyle on 10/18/15.
 */
public class Monster extends Character {

    public String defaultFile;
    public int defaultMaxFrames, defaultMaxSpawns;
    public Spawner spawner;
    public String battleDataFile;

    public void setDefaults(){}

    public static Monster getMonster(String type){
        if (type.equals("Slime")){
            return new Slime();
        }
        return null;
    }

}
