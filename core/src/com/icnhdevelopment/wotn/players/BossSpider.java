package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyle on 5/20/16.
 */
public class BossSpider extends Monster {

    public BossSpider(){
        setDefaults();
    }

    @Override
    public void setDefaults() {
        defaultFile = "characters/images/";
        prefix = "SpiderBoss";
        defaultMaxFrames = 1;
        defaultMaxSpawns = 1;
    }

    public void create(String filelocation, String prefix, int maxFrames, Vector2 position, int animSpeed, boolean player, boolean direcMove){
        super.create(filelocation, prefix, maxFrames, position, animSpeed, player, direcMove);
        direction = 1;
    }

    public Object[] possibleActions(){
        //Temporary
        return new Object[] { "Arange", "Aslash", "Sprotect", "Sfocus" };
    }

}
