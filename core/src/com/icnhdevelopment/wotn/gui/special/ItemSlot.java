package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.ImageLabel;
import com.icnhdevelopment.wotn.items.Item;

/**
 * Created by kyle on 1/13/16.
 */
public class ItemSlot extends ImageLabel {

    Item item;

    public ItemSlot(Container pa, Vector2 pos, Vector2 sz, Texture im){
        super(pa, pos, sz, im);

    }
}
