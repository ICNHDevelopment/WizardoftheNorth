package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.players.Character;
import com.icnhdevelopment.wotn.players.CharacterStats;
import com.icnhdevelopment.wotn.players.Monster;

import java.util.ArrayList;

/**
 * Created by kyle on 1/24/16.
 */
public class BattleInfo {

    Texture battleTex;
    String backFile;
    ArrayList<Character> protSide;
    ArrayList<Character> antSide;

    public BattleInfo(Character ch1, Character ch2){
        protSide = new ArrayList<>();
        antSide = new ArrayList<>();
        protSide.add(ch1);
        if (ch2 instanceof Monster){
            Monster monster = (Monster)ch2;
            String[] monstas = Gdx.files.internal(monster.battleDataFile).readString().replace("\n", "").split(";");
            for (int i = 1; i<monstas.length; i++){
                String[] data = monstas[i].split(":");
                String type = data[0];
                int level = Integer.valueOf(data[1]);
                int vit = Integer.valueOf(data[2]);
                int agl = Integer.valueOf(data[3]);
                int res = Integer.valueOf(data[4]);
                int str = Integer.valueOf(data[5]);
                int wis = Integer.valueOf(data[6]);
                CharacterStats cs = new CharacterStats(level, vit, agl, res, str, wis);
                Monster m = Monster.getMonster(type);
                m.create(m.defaultFile, m.defaultMaxFrames, new Vector2(0, 0), 2, false, cs);
                antSide.add(m);
            }
        } else {
            antSide.add(ch2);
        }
    }

    public String getBackFile() {
        return backFile;
    }

    public void setBackFile(String backFile) {
        this.backFile = backFile;
    }

    public Texture getBattleTex() {
        return battleTex;
    }

    public void setBattleTex(Texture battleTex) {
        this.battleTex = battleTex;
    }

    public ArrayList<Character> getProtSide() {
        return protSide;
    }

    public ArrayList<Character> getAntSide() {
        return antSide;
    }
}
