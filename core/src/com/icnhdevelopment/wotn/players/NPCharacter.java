package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by kyle on 5/21/16.
 */
public class NPCharacter extends Character {

    public String defaultFile, prefix;
    String dataFile;
    boolean interactable;
    Texture interactImage;

    ArrayList<String> dialogues;
    int currentDialogue = 0;

    public NPCharacter(String pref){
        prefix = pref;
        dialogues = new ArrayList<>();
        setDefaults();
        loadDialogues();
    }

    public void create(String filelocation, String prefix, int maxFrames, Vector2 position, int animSpeed, boolean player, boolean direcMove, String name){
        super.create(filelocation, prefix, maxFrames, position, animSpeed, player, direcMove);
        this.name = name;
        direction = 0;
        try{
            interactImage = new Texture(filelocation + prefix + "H.png");
        } catch (Exception e){}
    }

    public void setDefaults(){
        dataFile = "characters/stats/" + prefix + ".txt";
    }

    void loadDialogues(){
        String[] lines = Gdx.files.internal(dataFile).readString().replace("\n", "").replace("\r", "").split(";");
        for (int i = 0; i<lines.length; i++){
            dialogues.add(lines[i]);
        }
    }

    public void interact(){

    }

    public boolean isInteractable(){
        return interactable;
    }

    public void setInteractable(boolean i){
        interactable = i;
        if (i && interactImage != null){
            currentTexture = interactImage;
        } else {
            currentTexture = texture;
        }
    }

    public void render(SpriteBatch batch){
        if (interactable){
            int scale = 1;
            TextureRegion tr = TextureRegion.split(currentTexture, (int)regWidth, (int)(regHeight+2))[direction][0];
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
        } else {
            super.render(batch);
        }
    }

}
