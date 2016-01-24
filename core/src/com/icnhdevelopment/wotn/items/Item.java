package com.icnhdevelopment.wotn.items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.icnhdevelopment.wotn.gui.special.SlotType;

import java.util.HashMap;

/**
 * Created by kyle on 1/13/16.
 */
public class Item {

    public static HashMap<String, Item> ITEMS;

    public static void InitItems(){
        ITEMS = new HashMap<>();
        String[] items = Gdx.files.internal("Items/Items.txt").readString().replace("\n", "").replace("\r", "").split(";");
        for (int i = 1; i<items.length; i++){
            String t = items[i];
            String[] data = t.split(":");
            String k = data[0];
            SlotType st = SlotType.valueOf(data[1]);
            boolean spec = Boolean.valueOf(data[2]);
            Texture tex = new Texture("Items/" + k + ".png");
            if (spec){
                SpecialItem si = new SpecialItem(tex, st);
                ITEMS.put(k, si);
            }else {
                Item it = new Item(tex);
                ITEMS.put(k, it);
            }
        }
    }

    public Texture image;
    private SlotType type = SlotType.NORM;

    public Item(Texture im){
        image = im;
    }

    public Item(Item i){
        this.image = i.image;
        this.type = i.type;
    }

    public SlotType getType() {
        return type;
    }

    public void setType(SlotType type) {
        this.type = type;
    }
}
