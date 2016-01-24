package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.special.SlotType;
import com.icnhdevelopment.wotn.items.Item;

import java.util.ArrayList;

/**
 * Created by kyle on 1/22/16.
 */
public class InventoryObject extends CollideObject {

    Texture interactImage;
    Texture openImage;
    Texture currentImage;

    private boolean opened = false;

    private ArrayList<Item> items;

    public void create(String filename, Vector2 position, Vector2 size, boolean b, SlotType st, String name, String invenFile){
        super.create(filename, position, size, b, st);

        this.items = new ArrayList<>();
        String[] items = Gdx.files.internal(invenFile + ".txt").readString().replace("\n", "").split(";");
        for (int i = 0; i<items.length; i++){
            this.getItems().add(new Item(Item.ITEMS.get(items[i])));
        }
        currentImage = this.texture;
        String loc = filename.substring(0, filename.lastIndexOf("/")+1);
        interactImage = new Texture(loc + name + "H.png");
        openImage = new Texture(loc + name + "O.png");
    }

    public void render(SpriteBatch batch){
        batch.draw(currentImage, getPosition().x, getPosition().y, width, height);
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

    public ArrayList<Item> getItems() {
        return items;
    }
}
