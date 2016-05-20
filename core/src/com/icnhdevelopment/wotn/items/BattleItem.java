package com.icnhdevelopment.wotn.items;

import com.badlogic.gdx.graphics.Texture;
import com.icnhdevelopment.wotn.players.Character;

import java.util.ArrayList;

/**
 * Created by kyle on 5/18/16.
 */
public class BattleItem extends Item {

    String modStat;
    int value;

    public BattleItem(Texture im, String stat) {
        super(im);

        modStat = stat;
    }

    public BattleItem(BattleItem b){
        super (b);
        this.modStat = b.modStat;
        this.value = b.value;
    }

    public void performFunction(Character character){
        switch(modStat){
            case "VIT":
                character.heal(value);
                break;
            case "WIS":
                character.remember(value);
                break;
            case "AGL":
                character.getCharacterStats().addModifiers(new int[] { 0, 1, 0, 0, 0});
                break;
            case "RES":
                character.getCharacterStats().addModifiers(new int[] { 0, 0, 1, 0, 0});
                break;
            case "STR":
                character.getCharacterStats().addModifiers(new int[] { 0, 0, 0, 1, 0});
                break;
        }
    }

    public ArrayList<String> getTooltipData(){
        ArrayList<String> r = new ArrayList<>();
        r.add(name);
        String stat = "Joe", type = "Sheila";
        switch(modStat){
            case "VIT":
                stat = "Vitality";
                type = "Restores";
                break;
            case "WIS":
                stat = "Wisdom";
                type = "Restores";
                break;
            case "AGL":
                stat = "Agility";
                type = "Bonus";
                break;
            case "RES":
                stat = "Resistance";
                type = "Bonus";
                break;
            case "STR":
                stat = "Strength";
                type = "Bonus";
                break;
        }
        if (type.equals("Restores")){
            String temp = type + " " + value + " " + stat;
            r.add(temp);
        } else if (type.equals("Bonus")){
            String temp = "+" + value + " " + type + " " + stat;
            r.add(temp);
        }
        r.add("Upon Consumption");
        return r;
    }
}
