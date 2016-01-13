package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Alignment;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.ImageLabel;
import com.icnhdevelopment.wotn.items.Item;

import java.util.ArrayList;

/**
 * Created by kyle on 1/13/16.
 */
public class Inventory extends Container {

    ArrayList<ItemSlot> defaultInventory;
    ImageLabel invenImage;
    Vector2 textureSize;

    public Inventory(){
        super();
        create();
        defaultInventory = new ArrayList<>();
        loadInventorySlots();
    }

    void create(){
        textureSize = new Vector2(192*2, 288*2);
        invenImage = new ImageLabel(this, new Vector2((Game.WIDTH()-(192*2))/2, (Game.HEIGHT()-(288*2))/2), textureSize, new Texture(Gdx.files.internal("ui/inventory/Inventory.png")));
        invenImage.setImagealignment(Alignment.STRETCHED);
        this.children.add(invenImage);
    }

    void loadInventorySlots(){
        //Default
        int startX = 10, startY = 350;
        for (int i = 0; i<3; i++){
            for (int j = 0; j<4; j++){
                Item it = new Item(new Texture("Items/AmEmerald.png"));
                if (j == 0){
                    it = new Item(new Texture("Items/Ale.png"));
                }
                ItemSlot is = new ItemSlot(invenImage, new Vector2(startX + (i*60), textureSize.y-(startY + (j*60))), new Vector2(60, 60), it.image);
                is.setImagealignment(Alignment.STRETCHED);
                defaultInventory.add(is);
            }
        }
        //Armor
        startX = 40;
        startY = 80;
        for (int j = 0; j<4; j++){
            Item it = new Item(new Texture("Items/AmEmerald.png"));
            if (j == 0){
                it = new Item(new Texture("Items/Ale.png"));
            }
            ItemSlot is = new ItemSlot(invenImage, new Vector2(startX, textureSize.y-(startY + (j*64))), new Vector2(60, 60), it.image);
            is.setImagealignment(Alignment.STRETCHED);
            defaultInventory.add(is);
        }
        startX = 284;
        for (int j = 0; j<4; j++){
            Item it = new Item(new Texture("Items/AmEmerald.png"));
            if (j == 0){
                it = new Item(new Texture("Items/Ale.png"));
            }
            ItemSlot is = new ItemSlot(invenImage, new Vector2(startX, textureSize.y-(startY + (j*64))), new Vector2(60, 60), it.image);
            is.setImagealignment(Alignment.STRETCHED);
            defaultInventory.add(is);
        }
        Item it = new Item(new Texture("Items/DaggerStone.png"));
        ItemSlot is = new ItemSlot(invenImage, new Vector2(160, textureSize.y-(286)), new Vector2(60, 60), it.image);
        is.setImagealignment(Alignment.STRETCHED);
        defaultInventory.add(is);
    }

    public void render(SpriteBatch batch){
        if (visible) {
            renderBackground(batch);
            renderChildren(batch);
        }
    }

}
