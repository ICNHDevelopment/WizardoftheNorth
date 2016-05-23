package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by kyle on 10/18/15.
 */
public class Monster extends Character {

    public String defaultFile, prefix;
    public int defaultMaxFrames, defaultMaxSpawns;
    public Spawner spawner;
    public String battleDataFile;

    public void setDefaults(){}

    public static Monster getMonster(String type){
        if (type.equals("BlueSlime")){
            return new Slime(0);
        }
        if (type.equals("GreenSlime")){
            return new Slime(1);
        }
        if (type.equals("SpiderBoss")){
            return new BossSpider();
        }
        return null;
    }

    public Object[] possibleActions(){
        //Temporary
        return new Object[] { "Arange", "Sprotect", "Sfocus" };
    }

}
