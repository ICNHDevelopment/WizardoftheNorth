package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
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
        //interactImage = new Texture("texturehere");
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

}
