package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.ImageLabel;
import com.icnhdevelopment.wotn.items.Item;

/**
 * Created by kyle on 1/13/16.
 */
public class ItemSlot extends ImageLabel {

    Item item;
    SlotType type = SlotType.NORM;
    Texture defaultImage;

    public ItemSlot(Container pa, Vector2 pos, Vector2 sz, Texture im){
        super(pa, pos, sz, im);

    }

    public void render(SpriteBatch batch){
        if (visible) {
            batch.begin();
            if (item==null){
                if (type != SlotType.NORM){
                    batch.draw(defaultImage, getAbsolutePosition().x + imagePosition.x, getAbsolutePosition().y + imagePosition.y, imageSize.x*2, imageSize.y*2);
                }
            }
            if (isHovering()){
                batch.draw(hoverImage, getAbsolutePosition().x + imagePosition.x, getAbsolutePosition().y + imagePosition.y, imageSize.x*2, imageSize.y*2);
            }
            if (item!=null) {
                batch.draw(item.image, getAbsolutePosition().x + imagePosition.x, getAbsolutePosition().y + imagePosition.y, imageSize.x*2, imageSize.y*2);
            }
            batch.end();
        }
    }
}
