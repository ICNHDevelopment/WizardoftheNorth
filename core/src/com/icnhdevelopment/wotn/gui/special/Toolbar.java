package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Alignment;

/**
 * Created by kyle on 1/15/16.
 */
public class Toolbar extends Inventory {

    public Toolbar(String file){
        super(file);
    }

    void create(String file){
        super.create(file);
        this.invenImage.reposition(0, Game.HEIGHT()-(60+invenImage.getSize().y));
    }

    void loadInventorySlots(){
        //Default
        int startX = 0, startY = 0;
        for (int j = 0; j<4; j++){
            ItemSlot is = new ItemSlot(invenImage, new Vector2(startX, textureSize.y-(startY + ((j+1)*60))), new Vector2(60, 60), null, false);
            is.setHoverImage(new Texture("Items/highlight.png"));
            is.setImagealignment(Alignment.STRETCHED);
            defaultInventory.add(is);
        }
    }

}
