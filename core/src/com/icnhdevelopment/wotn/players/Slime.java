package com.icnhdevelopment.wotn.players;

/**
 * Created by kyle on 10/18/15.
 */
public class Slime extends Monster{

    public Slime(){
        setDefaults();
    }

    @Override
    public void setDefaults() {
        defaultFile = "characters/images/SlimeSS.png";
        defaultMaxFrames = 9;
        defaultMaxSpawns = 3;
    }
}
