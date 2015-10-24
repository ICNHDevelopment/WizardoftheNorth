package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.world.World;

import java.util.Date;
import java.util.Random;
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

    public void create(String filename, int maxFrames, Vector2 position, Vector2 size, int animSpeed){
        super.create(filename, position, size);
        this.maxFrames = maxFrames;
        speed = animSpeed;
        regWidth =  texture.getWidth()/maxFrames;
        regHeight = texture.getHeight();
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
        TextureRegion tr = TextureRegion.split(texture, (int)regWidth, (int)regHeight)[0][frame];
        batch.draw(tr, position.x, position.y, width, height);
    }

    public boolean isAnimating() { return animating; }
}
