package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.world.World;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by kyle on 10/18/15.
 */
public class Spawner {

    Monster type;
    Vector2 center;
    Timer spawner;
    World world;
    int delay;
    int maxChildren, children;
    public boolean spawn = false;

    public Spawner(Monster type, World w, Vector2 centers){
        this.type = type;
        this.world = w;
        this.maxChildren = type.defaultMaxSpawns;
        this.center = centers;
    }

    public void start(){
        spawner = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                if (children < maxChildren) {
                    delay = (10 + new Random().nextInt(20)) * 1000;
                    spawn = true;
                    System.out.println("Summoned " + type.getClass().getSimpleName());
                    spawner.cancel();
                    start();
                }
            }
        };
        spawner.schedule(tt, delay);
    }

    public void spawn() {
        Monster m = null;
        if (type instanceof Slime){
            m = new Slime();
        }
        if (m!=null) {
            int tx, ty;
            tx = (int) (center.x - 4 + new Random().nextInt(8));
            ty = (int) (center.y - 4 + new Random().nextInt(8));
            m.create(m.defaultFile, m.defaultMaxFrames, new Vector2(tx, ty), 2, false, false);
            m.spawner = this;
            world.spawn(m);
            children++;
        }
    }

}
