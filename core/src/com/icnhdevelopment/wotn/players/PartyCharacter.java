package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.Gdx;

/**
 * Created by kyle on 5/21/16.
 */
public class PartyCharacter extends NPCharacter {

    public PartyCharacter(String pref){
        super(pref);
        loadStats();
    }

    void loadStats(){
        String[] lines = Gdx.files.internal(dataFile).readString().replace("\n", "").replace("\r", "").split(";");
        String[] stats = lines[lines.length-1].split(":");
        int lv = Integer.valueOf(stats[0]);
        int vit = Integer.valueOf(stats[1]);
        int agi = Integer.valueOf(stats[2]);
        int res = Integer.valueOf(stats[3]);
        int str = Integer.valueOf(stats[4]);
        int wis = Integer.valueOf(stats[5]);
        this.stats = new CharacterStats(this, lv, vit, agi, res, str, wis);
    }

    void loadDialogues(){
        String[] lines = Gdx.files.internal(dataFile).readString().replace("\n", "").replace("\r", "").split(";");
        for (int i = 0; i<lines.length-1; i++){
            dialogues.add(lines[i]);
        }
    }

    public void setDefaults(){
        defaultFile = "characters/images/";
        dataFile = "characters/stats/" + prefix + ".txt";
    }

    public void interact(){

    }
}
