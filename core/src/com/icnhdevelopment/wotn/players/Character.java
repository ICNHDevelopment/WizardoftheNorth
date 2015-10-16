package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.icnhdevelopment.wotn.world.World;

/**
 * Created by kyle on 6/4/15.
 */
public class Character {

    Texture texture;
    int direction, frame, maxFrames;
    float regWidth, regHeight;
    int x, y, width, height;
    boolean animating = false;

    public void create(String filename, int maxFrames){
        texture = new Texture(filename);
        this.maxFrames = maxFrames;
        regWidth =  texture.getWidth()/maxFrames;
        regHeight = texture.getHeight()/2;
        width = (int)(regWidth);//*World.SCALE);
        height = (int)(regHeight);//* World.SCALE);
        frame = 0;
        direction = 0;
        x = 1;
        y = -1;
    }

    public void animate(){
        frame++;
        if (frame>=maxFrames){
            animating = false;
            frame = 0;
        }
    }

    public void render(SpriteBatch batch){
        TextureRegion tr = TextureRegion.split(texture, (int)regWidth, (int)regHeight)[direction][frame];
        batch.draw(tr, x*World.TileWidth, y*World.TileHeight, width, height);
    }

    public boolean isAnimating() { return animating; }

}
