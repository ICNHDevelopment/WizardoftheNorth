package com.icnhdevelopment.wotn.players;

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
        defaultMaxFrames = 9;
        defaultMaxSpawns = 3;
    }

}
