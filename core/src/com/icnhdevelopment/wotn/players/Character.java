package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.world.World;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by kyle on 6/4/15.
 */
public class Character {

    Texture texture;
    int direction, frame, maxFrames;
    float regWidth, regHeight;
    Vector2 position, tilePosition;
    int width, height;
    boolean animating = false;

    final int SPEED = 2;

    public void create(String filename, int maxFrames, Vector2 position){
        texture = new Texture(filename);
        this.maxFrames = maxFrames;
        regWidth =  texture.getWidth()/maxFrames;
        regHeight = texture.getHeight()/2;
        width = (int)(regWidth);//*World.SCALE);
        height = (int)(regHeight);//* World.SCALE);
        frame = 0;
        direction = 0;
        this.position = position;
    }

    public void animate(boolean moving) {
        if (World.TICK % 5 == 0) {
            if (moving) {
                frame++;
                if (frame >= maxFrames) {
                    animating = false;
                    frame = 0;
                }
            } else if (frame > 0) {
                if (frame < maxFrames / 2) {
                    frame--;
                } else {
                    frame++;
                    if (frame >= maxFrames) {
                        animating = false;
                        frame = 0;
                    }
                }
            }
        }
    }

    public void move(Vector2 amount, ArrayList<Rectangle> walls){
        animate(true);
        Rectangle next = new Rectangle(position.x + amount.x*SPEED, position.y + amount.y*SPEED, width, height);
        Rectangle nextFootBox = new Rectangle(next.x+width*.2f, next.y, width*.6f, height*.15f);
        for (Rectangle r : walls){
            if (r.overlaps(nextFootBox)){
                return;
            }
        }
        position.x += amount.x*SPEED;
        position.y += amount.y*SPEED;
        if (amount.y == 0){
            if (amount.x>0){
                direction = 1;
            }else{
                direction = 0;
            }
        }
    }

    public void render(SpriteBatch batch){
        TextureRegion tr = TextureRegion.split(texture, (int)regWidth, (int)regHeight)[direction][frame];
        batch.draw(tr, position.x, position.y, width, height);
    }

    public boolean isAnimating() { return animating; }
    public Vector2 getPosition() { return position; }
    public Vector2 getSize() { return new Vector2(width, height); }

}
