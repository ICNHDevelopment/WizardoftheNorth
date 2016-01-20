package com.icnhdevelopment.wotn.items;

import com.badlogic.gdx.graphics.Texture;
import com.icnhdevelopment.wotn.gui.special.SlotType;

/**
 * Created by kyle on 1/15/16.
 */
public class SpecialItem extends Item {

    public SpecialItem(Texture im, SlotType type){
        super(im);
        this.setType(type);
    }
}
