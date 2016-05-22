package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.Gdx;

/**
 * Created by kyle on 5/21/16.
 */
public class PartyCharacter extends NPCharacter {

    String battleFile;

    public PartyCharacter(String pref){
        prefix = pref;
        setDefaults();
        loadStats();
    }

    void loadStats(){
        String[] stats = Gdx.files.internal(battleFile).readString().replace("\n", "").replace("\r", "").split(":");
        int lv = Integer.valueOf(stats[0]);
        int vit = Integer.valueOf(stats[1]);
        int agi = Integer.valueOf(stats[2]);
        int res = Integer.valueOf(stats[3]);
        int str = Integer.valueOf(stats[4]);
        int wis = Integer.valueOf(stats[5]);
        CharacterStats stets = new CharacterStats(this, lv, vit, agi, res, str, wis);
        this.stats = stets;
    }

    public void setDefaults(){
        defaultFile = "characters/images/";
        battleFile = "characters/stats/" + prefix + ".txt";
    }

    public void interact(){

    }
}
