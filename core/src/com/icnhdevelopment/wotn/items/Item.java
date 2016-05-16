package com.icnhdevelopment.wotn.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.gui.special.SlotType;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by kyle on 1/13/16.
 */
public class Item {

    public static HashMap<String, Item> ITEMS;
    public static HashMap<String, SpecialItem> SPECIAL_ITEMS;

    public static void InitItems(){
        ITEMS = new HashMap<>();
        SPECIAL_ITEMS = new HashMap<>();
        String[] items = Gdx.files.internal("Items/Items.txt").readString().replace("\n", "").replace("\r", "").split(";");
        for (int i = 1; i<items.length; i++){
            String t = items[i];
            String[] data = t.split(":");
            String k = data[0];
            String name = data[1];
            SlotType st = SlotType.valueOf(data[2]);
            boolean spec = Boolean.valueOf(data[3]);
            Texture tex = new Texture("Items/" + k + ".png");
            Texture over = null;
            try { over = new Texture("Items/" + k + "E.png"); } catch (Exception e){}
            if (spec){
                SpecialItem si = new SpecialItem(tex, st);
                si.name = name;
                si.VitalityBonus = Integer.valueOf(data[4]);
                si.AgilityBonus = Integer.valueOf(data[5]);
                si.ResistanceBonus = Integer.valueOf(data[6]);
                si.StrengthBonus = Integer.valueOf(data[7]);
                si.WisdomBonus = Integer.valueOf(data[8]);
                if (over!=null){
                    si.setCharacterOverlay(over);
                }
                SPECIAL_ITEMS.put(k, si);
            }else {
                Item it = new Item(tex);
                it.name = name;
                ITEMS.put(k, it);
            }
        }
    }

    public Texture image;
    private SlotType type = SlotType.NORM;
    String name;

    public Item(Texture im){
        image = im;
    }

    public Item(Item i){
        this.image = i.image;
        this.type = i.type;
        this.name = i.name;
    }

    public SlotType getType() {
        return type;
    }

    public void setType(SlotType type) {
        this.type = type;
    }

    public ArrayList<String> getTooltipData(){
        ArrayList<String> r = new ArrayList<>();
        r.add(name);
        return r;
    }
}
