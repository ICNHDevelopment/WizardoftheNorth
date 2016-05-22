package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyle on 5/21/16.
 */
public class NPCharacter extends Character {

    public String defaultFile, prefix;
    boolean interactable;
    Texture interactImage;

    public void create(String filelocation, String prefix, int maxFrames, Vector2 position, int animSpeed, boolean player, boolean direcMove, String name){
        super.create(filelocation, prefix, maxFrames, position, animSpeed, player, direcMove);
        this.name = name;
        interactImage = texture;
    }

    public void setDefaults(){}

    public void interact(){

    }

    public boolean isInteractable(){
        return interactable;
    }

    public void setInteractable(boolean i){
        interactable = i;
        if (i){
            currentTexture = interactImage;
        } else {
            currentTexture = texture;
        }
    }

    public void render(SpriteBatch batch){
        if (interactable){
            int scale = 1;
            TextureRegion tr = TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[direction][0];
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
        } else {
            super.render(batch);
        }
    }

}
