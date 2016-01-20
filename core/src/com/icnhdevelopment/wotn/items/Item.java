package com.icnhdevelopment.wotn.items;

import com.badlogic.gdx.graphics.Texture;
import com.icnhdevelopment.wotn.gui.special.SlotType;

/**
 * Created by kyle on 1/13/16.
 */
public class Item {

    public Texture image;
    private SlotType type = SlotType.NORM;

    public Item(Texture im){
        image = im;
    }

    public SlotType getType() {
        return type;
    }

    public void setType(SlotType type) {
        this.type = type;
    }
}
