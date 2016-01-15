package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Alignment;
import com.icnhdevelopment.wotn.gui.ImageLabel;
import com.icnhdevelopment.wotn.items.Item;

/**
 * Created by kyle on 1/15/16.
 */
public class Toolbar extends Inventory {

    public Toolbar(String file){
        super(file);
    }

    void loadInventorySlots(){
        //Default
        int startX = 0, startY = 0;
        for (int i = 0; i<3; i++){
            for (int j = 0; j<4; j++){
                Item it = new Item(new Texture("Items/Ale.png"));
                if (j == 0){
                    it = new Item(new Texture("Items/AmEmerald.png"));
                    it.setType(SlotType.AMULET);
                }
                if (i==2&&j==1){
                    it = new Item(new Texture("Items/ChestIron.png"));
                    it.setType(SlotType.CHEST);
                }
                ItemSlot is = new ItemSlot(invenImage, new Vector2(startX + (i*60), textureSize.y-(startY + (j*60))), new Vector2(60, 60), it.image);
                is.setHoverImage(new Texture("Items/highlight.png"));
                is.item = it;
                is.setImagealignment(Alignment.STRETCHED);
                defaultInventory.add(is);
            }
        }
    }

}
