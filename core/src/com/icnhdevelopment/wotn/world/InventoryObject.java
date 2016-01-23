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

    private boolean opened = false;

    public void create(String filename, Vector2 position, Vector2 size, boolean b, SlotType st, String name, String invenFile){
        super.create(filename, position, size, b, st);

        currentImage = this.texture;
        String loc = filename.substring(0, filename.lastIndexOf("/")+1);
        interactImage = new Texture(loc + name + "H.png");
        openImage = new Texture(loc + name + "O.png");
    }

    public void render(SpriteBatch batch){
        batch.draw(currentImage, position.x, position.y, width, height);
    }

    public boolean isOpened() {
        return opened;
    }

    public void setInteractable(boolean i){
        if (!opened){
            this.interactable = i;
            if (i){
                currentImage = interactImage;
            } else{
                currentImage = texture;
            }
        }
    }

    public void setOpened(boolean opened) {
        this.opened = opened;
        if (opened){
            currentImage = openImage;
        }
    }
}
