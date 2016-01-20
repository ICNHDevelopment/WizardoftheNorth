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
    protected SlotType slotType = SlotType.NORM;
    protected Texture defaultImage;

    boolean isBlocked;

    public ItemSlot(Container pa, Vector2 pos, Vector2 sz, Texture im, boolean block){
        super(pa, pos, sz, im);

        isBlocked = block;
        if (isBlocked){
            item = null;
            setDefaultImage(new Texture("Items/Blocked.png"));
        }
    }

    public void render(SpriteBatch batch){
        if (visible) {
            if (isBlocked){
                item = null;
                setDefaultImage(new Texture("Items/Blocked.png"));
            }
            batch.begin();
            if (isHovering()) {
                batch.draw(hoverImage, getAbsolutePosition().x + imagePosition.x, getAbsolutePosition().y + imagePosition.y, imageSize.x * 2, imageSize.y * 2);
            }
            if (item == null) {
                if (getSlotType() != SlotType.NORM || isBlocked) {
                    batch.draw(getDefaultImage(), getAbsolutePosition().x + imagePosition.x, getAbsolutePosition().y + imagePosition.y, imageSize.x * 2, imageSize.y * 2);
                }
            }
            if (item != null) {
                batch.draw(item.image, getAbsolutePosition().x + imagePosition.x, getAbsolutePosition().y + imagePosition.y, imageSize.x * 2, imageSize.y * 2);
            }
            batch.end();
        }
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public void setSlotType(SlotType slotType) {
        this.slotType = slotType;
    }

    public Texture getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(Texture defaultImage) {
        this.defaultImage = defaultImage;
    }
}
