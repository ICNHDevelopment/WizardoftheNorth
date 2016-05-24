package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.icnhdevelopment.wotn.items.Item;

import java.util.ArrayList;

/**
 * Created by kyle on 10/18/15.
 */
public class Monster extends Character {

    public String defaultFile, prefix;
    public int defaultMaxFrames, defaultMaxSpawns;
    public Spawner spawner;
    public String battleDataFile;

    ArrayList<Item> drops;

    public void setDefaults(){}

    public static Monster getMonster(String type){
        if (type.equals("BlueSlime")){
            return new Slime(0);
        }
        if (type.equals("GreenSlime")){
            return new Slime(1);
        }
        if (type.equals("SpiderBoss")){
            return new BossSpider();
        }
        return null;
    }

    public Object[] possibleActions(){
        //Temporary
        return new Object[] { "Arange", "Sprotect", "Sfocus" };
    }

    public void addDrop(Item i){
        if (drops==null){
            drops = new ArrayList<>();
        }
        drops.add(i);
    }

    public ArrayList<Item> getDrops(){
        return drops;
    }

}
