package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.world.World;

import java.util.*;

/**
 * Created by kyle on 6/4/15.
 */
public class Character extends AnimatedSprite {

    int direction;
    Vector2 tilePosition;
    Rectangle footBox;
    boolean player;
    boolean isTransitioningLayer = false;
    boolean hasRandomMovement = false, isMovingRandomly = true;

    final int SPEED = 2;

    public void create(String filename, int maxFrames, Vector2 position, int animSpeed){
        super.create(filename, maxFrames, position, new Vector2(), animSpeed);
        regHeight = texture.getHeight()/2;
        width = (int)(regWidth);//*World.SCALE);
        height = (int)(regHeight);//* World.SCALE);
        frame = 0;
        direction = 0;
        footBox = new Rectangle(position.x+width*.2f, position.y, width*.6f, height*.15f);
    }

    public void move(Vector2 amount, ArrayList<Rectangle> walls){
        animate(true);
        Rectangle next = new Rectangle(position.x + amount.x*SPEED, position.y + amount.y*SPEED, width, height);
        footBox = new Rectangle(next.x+width*.2f, next.y, width*.6f, height*.15f);
        for (Rectangle r : walls){
            if (r.overlaps(footBox)){
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

    public void updateWalls(TiledMap map, ArrayList<Rectangle> overs){
        if (!isTransitioningLayer) {
            boolean changedLayer = false;
            for (Rectangle o : overs) {
                if (footBox.overlaps(o)) {
                    changedLayer = true;
                    if ((map.getLayers().get("overwalls").getOpacity()>0))
                        transitionLayer(map.getLayers().get("overwalls"), -.005f);
                    else
                        map.getLayers().get("overwalls").setOpacity(0f);
                }
            }
            if (!changedLayer){
                changedLayer = true;
                if ((map.getLayers().get("overwalls").getOpacity()<1))
                    transitionLayer(map.getLayers().get("overwalls"), .005f);
                else
                    map.getLayers().get("overwalls").setOpacity(1f);
            }
        }
    }

    void transitionLayer(MapLayer l, float step){
        final MapLayer m = l;
        final float s = step;
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                if ((s<0&&m.getOpacity()>0)||(m.getOpacity()<1&&s>0)){
                    isTransitioningLayer = true;
                    m.setOpacity(m.getOpacity()+s);
                }else{
                    isTransitioningLayer = false;
                    this.cancel();
                }
            }
        }, new Date(), 1);
    }

    public void setRandomMovementTimer(ArrayList<Rectangle> w){
        final ArrayList<Rectangle> walls = w;
        final Random r = new Random();

        final Thread movementThread = new Thread(){
            @Override
            public  void run(){
                boolean running = true;
                while (running) {
                    long delay = (long) (1000 * (2 + r.nextDouble() * 1));
                    try {
                        Thread.sleep(delay);
                        moveRandomly(walls);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        running = false;
                    }
                }
            }
        };

        movementThread.start();
    }

    void moveRandomly(ArrayList<Rectangle> w){
        if (isMovingRandomly){
            Random r = new Random();
            int xDir = r.nextInt(3)-1, yDir = r.nextInt(3)-1;
            while (xDir==0&&yDir==0){
                xDir = r.nextInt(3)-1;
                yDir = r.nextInt(3)-1;
            }
            animating = true;
            while (animating) {
                move(new Vector2(xDir, yDir), w);
                try {
                    Thread.sleep(speed*speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            frame = 0;
        }else {
            //Pathfind to a target
        }
    }

    public void animate(boolean moving) {
        if (World.TICK % speed == 0) {
            if (moving) {
                super.animate();
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

    public void render(SpriteBatch batch){
        TextureRegion tr = TextureRegion.split(texture, (int)regWidth, (int)regHeight)[direction][frame];
        batch.draw(tr, position.x, position.y, width, height);
    }

    public boolean isPlayer() { return player; }

    public Rectangle getHitBox() { return new Rectangle(footBox.x, footBox.y, footBox.width, footBox.height); }
}
