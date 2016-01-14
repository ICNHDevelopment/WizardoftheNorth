package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Alignment;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.ImageLabel;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.items.Item;

import java.util.ArrayList;

/**
 * Created by kyle on 1/13/16.
 */
public class Inventory extends Container {

    ArrayList<ItemSlot> defaultInventory;
    ImageLabel invenImage;
    Vector2 textureSize;

    Item mouseItem = null;
    Vector2 mousePosition;

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
        //Armor
        startX = 40;
        startY = 80;
        for (int j = 0; j<4; j++){
            ItemSlot is = new ItemSlot(invenImage, new Vector2(startX, textureSize.y-(startY + (j*64))), new Vector2(60, 60), null);
            if (j == 0){
                is.type = SlotType.HEAD;
                is.defaultImage = new Texture("Items/DefaultHelm.png");
            }
            else if (j==1){
                is.type = SlotType.CHEST;
                is.defaultImage = new Texture("Items/DefaultChest.png");
            }
            else if (j==2){
                is.type = SlotType.LEGS;
                is.defaultImage = new Texture("Items/DefaultLegs.png");
            }
            else if (j==3){
                is.type = SlotType.FEET;
                is.defaultImage = new Texture("Items/DefaultBoots.png");
            }
            is.setHoverImage(new Texture("Items/highlight.png"));
            is.setImagealignment(Alignment.STRETCHED);
            defaultInventory.add(is);
        }
        startX = 282;
        for (int j = 0; j<4; j++){
            ItemSlot is = new ItemSlot(invenImage, new Vector2(startX, textureSize.y-(startY + (j*64))), new Vector2(60, 60), null);
            if (j == 0){
                is.type = SlotType.AMULET;
                is.defaultImage = new Texture("Items/DefaultAm.png");
            }
            else if (j==1){
                is.type = SlotType.GAUNT;
                is.defaultImage = new Texture("Items/DefaultGauntlets.png");
            }
            else if (j==2||j==3){
                is.type = SlotType.RING;
                is.defaultImage = new Texture("Items/DefaultRing.png");
            }
            is.setHoverImage(new Texture("Items/highlight.png"));
            is.setImagealignment(Alignment.STRETCHED);
            defaultInventory.add(is);
        }
        ItemSlot is = new ItemSlot(invenImage, new Vector2(160, textureSize.y-(286)), new Vector2(60, 60), null);
        is.type = SlotType.WEAPON;
        is.defaultImage = new Texture("Items/DefaultWeapon.png");
        is.setHoverImage(new Texture("Items/highlight.png"));
        is.setImagealignment(Alignment.STRETCHED);
        defaultInventory.add(is);
    }

    public void update(CInputProcessor processor){
        for (ItemSlot is : defaultInventory){
            if (processor.mouseHovered(is.getAbsolutePosition().x, is.getAbsolutePosition().y, is.getSize().x, is.getSize().y)){
                is.setHovering(true);
                if (processor.didMouseClick()){
                    if (mouseItem!=null){
                        if (mouseItem.getType().equals(is.type)||is.type.equals(SlotType.NORM)){
                            Item temp = is.item;
                            is.item = mouseItem;
                            mouseItem = temp;
                        }
                    }else{
                        Item temp = is.item;
                        is.item = mouseItem;
                        mouseItem = temp;
                    }
                }
            }
            else{
                is.setHovering(false);
            }
        }
        mousePosition = processor.getMousePosition();
    }

    public void render(SpriteBatch batch){
        if (visible) {
            renderBackground(batch);
            renderChildren(batch);
            if (mouseItem!=null){
                batch.begin();
                batch.draw(mouseItem.image, mousePosition.x-mouseItem.image.getWidth()/2, mousePosition.y-mouseItem.image.getHeight()/2, mouseItem.image.getWidth(), mouseItem.image.getHeight());
                batch.end();
            }
        }
    }

}
