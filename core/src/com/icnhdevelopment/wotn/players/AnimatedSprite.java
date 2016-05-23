package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.world.World;

import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kyle on 10/23/15.
 */
public class AnimatedSprite extends Sprite {

    int frame, maxFrames, speed;
    float regWidth, regHeight;
    boolean animating = false;
    Timer animator;
    boolean directionalMovement = true;

    Texture currentTexture;
    Texture attackAnimation;
    Texture rangeAnimation;
    Texture consumeAnimation;
    Texture deadAnimation;

    public void create(String filelocation, String prefix, int maxFrames, Vector2 position, Vector2 size, int animSpeed){
        super.create(filelocation+prefix+"SS.png", position, size);
        this.maxFrames = maxFrames;
        speed = animSpeed;
        regWidth =  texture.getWidth()/maxFrames;
        regHeight = texture.getHeight();
        currentTexture = texture;
        try {
            attackAnimation = new Texture(filelocation + prefix + "AttackSS.png");
        } catch(Exception e){}
        try{
            rangeAnimation = new Texture(filelocation + prefix + "RangeSS.png");
        } catch(Exception e){}
        try{
            consumeAnimation = new Texture(filelocation + prefix + "DrinkSS.png");
        } catch(Exception e){}
        try{
            deadAnimation = new Texture(filelocation + prefix + "DeadSS.png");
        } catch(Exception e){}
        if (!(this instanceof Character)) start();
    }

    public void start(){
        animator = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                animate();
            }
        };
        animator.schedule(tt, new Date(), speed);
    }

    public void animate() {
        if (World.TICK % speed == 0) {
            frame++;
            if (frame >= maxFrames) {
                animating = false;
                frame = 0;
            }
        }
    }

    public void render(SpriteBatch batch){
        TextureRegion tr = TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[0][frame];
        batch.draw(tr, getPosition().x+drawOffset.x, getPosition().y+drawOffset.y, width, height);
    }

    public boolean isAnimating() { return animating; }

    public float getRegionWidth(){
        return regWidth;
    }

    public float getRegionHeight(){
        return regHeight;
    }
}
