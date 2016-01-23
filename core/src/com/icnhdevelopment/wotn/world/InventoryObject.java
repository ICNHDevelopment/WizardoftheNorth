package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.special.SlotType;

/**
 * Created by kyle on 1/22/16.
 */
public class InventoryObject extends CollideObject {

    Texture interactImage;
    Texture openImage;
    Texture currentImage;

    public void create(String filename, Vector2 position, Vector2 size, boolean b, SlotType st){
        super.create(filename, position, size, b, st);

        currentImage = this.texture;
    }

    public void render(SpriteBatch batch){
        batch.draw(currentImage, position.x, position.y, width, height);
    }
}
