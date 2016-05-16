package com.icnhdevelopment.wotn.players;

import com.icnhdevelopment.wotn.world.World;

/**
 * Created by kyle on 10/18/15.
 */

public class Slime extends Monster{

    boolean animUp = true;

    public Slime(){
        setDefaults();
    }

    @Override
    public void setDefaults() {
        defaultFile = "characters/images/SlimeSS.png";
        defaultAttack = "characters/images/blueSlimeAttackSS.png";
        defaultMaxFrames = 9;
        defaultMaxSpawns = 3;
    }

    public void animate() {
        if (World.TICK % speed == 0) {
            if (animUp) {
                frame++;
                this.getPosition().y+=2;
                if (frame>=maxFrames){
                    animUp = false;
                    frame--;
                    this.getPosition().y-=2;
                }
            }
            else{
                frame--;
                this.getPosition().y-=2;
            }
            if (frame <= 0) {
                animating = false;
                frame = 0;
                animUp = true;
            }
        }
    }
}
