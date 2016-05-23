package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.world.World;

/**
 * Created by kyle on 10/18/15.
 */

public class Slime extends Monster{

    boolean animUp = true;
    int type;

    public Slime(int type){
        this.type = type;
        setDefaults();
    }

    @Override
    public void setDefaults() {
        defaultFile = "characters/images/";
        if (type == 0) {
            prefix = "BlueSlime";
        } else {
            prefix = "GreenSlime";
        }
        defaultMaxFrames = 9;
        defaultMaxSpawns = 3;
    }

    public void create(String filelocation, String prefix, int maxFrames, Vector2 position, int animSpeed, boolean player, boolean direcMove){
        super.create(filelocation, prefix, maxFrames, position, animSpeed, player, direcMove);
        direction = 0;
    }

    public Object[] possibleActions(){
        //Temporary
        return new Object[] { "Arange", "Sprotect", "Sfocus" };
    }

    public void animate() {
        currentTexture = texture;
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
