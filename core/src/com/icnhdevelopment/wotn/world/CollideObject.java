package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.special.SlotType;
import com.icnhdevelopment.wotn.players.Sprite;

/**
 * Created by kyle on 1/22/16.
 */
public class CollideObject extends Sprite {

    private boolean breakable = false;
    private boolean visible;
    private SlotType breakItem = SlotType.NORM;

    public void create(String filename, Vector2 position, Vector2 size, boolean b, SlotType st){
        super.create(filename, position, size);

        breakable = b;
        breakItem = st;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isBreakable() {
        return breakable;
    }

    public SlotType getBreakItem() {
        return breakItem;
    }
}
