package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.*;
import com.icnhdevelopment.wotn.handlers.ButtonFuction;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.items.Item;
import com.icnhdevelopment.wotn.items.SpecialItem;
import com.icnhdevelopment.wotn.players.Character;

import java.util.ArrayList;

/**
 * Created by kyle on 1/13/16.
 */
public class Inventory extends Container {

    ArrayList<ItemSlot> defaultInventory;
    ArrayList<Rectangle> statSlots;
    ImageLabel invenImage;
    Vector2 textureSize;

    Item mouseItem = null;
    Vector2 mousePosition;

    Toolbar toolbar;
    Tooltip tooltip;

    Character character;

    BitmapFont font;

    public Inventory(String file){
        super();
        create(file);
        defaultInventory = new ArrayList<>();
        statSlots = new ArrayList<>();
        loadInventorySlots();
        loadStatSlots();
        tooltip = new Tooltip(this);
    }

    void create(String file){
        Texture tex = new Texture(Gdx.files.internal(file));
        textureSize = new Vector2(tex.getWidth()*2, tex.getHeight()*2);
        invenImage = new ImageLabel(this, new Vector2((Game.WIDTH()-textureSize.x)/2, (Game.HEIGHT()-textureSize.y)/2), textureSize, tex);
        invenImage.setImagealignment(Alignment.STRETCHED);
        this.children.add(invenImage);
        if (!(this instanceof Toolbar)) {
            Container button = new Container(invenImage, new Vector2(354, 544), new Vector2(26, 28));
            button.setFunc(ButtonFuction.VISIBILITY);
            button.setDesc(this);
            buttons.add(button);
        }
    }

    void loadInventorySlots(){
        //Default
        int startX = 10, startY = 350;
        for (int j = 0; j<4; j++){
            for (int i = 0; i<3; i++){
                boolean b = false;
                if (j>1) {
                    b = true;
                }
                ItemSlot is = new ItemSlot(invenImage, new Vector2(startX + (i*60), textureSize.y-(startY + (j*60))), new Vector2(60, 60), null, b);
                is.setHoverImage(new Texture("Items/highlight.png"));
                is.setImagealignment(Alignment.STRETCHED);
                defaultInventory.add(is);
            }
        }
        //Armor
        startX = 40;
        startY = 80;
        for (int j = 0; j<4; j++){
            ItemSlot is = new ItemSlot(invenImage, new Vector2(startX, textureSize.y-(startY + (j*64))), new Vector2(60, 60), null, false);
            if (j == 0){
                is.setSlotType(SlotType.HEAD);
                is.setDefaultImage(new Texture("Items/DefaultHelm.png"));
            }
            else if (j==1){
                is.setSlotType(SlotType.CHEST);
                is.setDefaultImage(new Texture("Items/DefaultChest.png"));
            }
            else if (j==2){
                is.setSlotType(SlotType.LEGS);
                is.setDefaultImage(new Texture("Items/DefaultLegs.png"));
            }
            else if (j==3){
                is.setSlotType(SlotType.FEET);
                is.setDefaultImage(new Texture("Items/DefaultBoots.png"));
            }
            is.setHoverImage(new Texture("Items/highlight.png"));
            is.setImagealignment(Alignment.STRETCHED);
            defaultInventory.add(is);
        }
        startX = 282;
        for (int j = 0; j<4; j++){
            ItemSlot is = new ItemSlot(invenImage, new Vector2(startX, textureSize.y-(startY + (j*64))), new Vector2(60, 60), null, false);
            if (j == 1){
                is.setSlotType(SlotType.AMULET);
                is.setDefaultImage(new Texture("Items/DefaultAm.png"));
            }
            else if (j==0){
                is.setSlotType(SlotType.GAUNT);
                is.setDefaultImage(new Texture("Items/DefaultGauntlets.png"));
            }
            else if (j==2||j==3){
                is.setSlotType(SlotType.RING);
                is.setDefaultImage(new Texture("Items/DefaultRing.png"));
            }
            is.setHoverImage(new Texture("Items/highlight.png"));
            is.setImagealignment(Alignment.STRETCHED);
            defaultInventory.add(is);
        }
        ItemSlot is = new ItemSlot(invenImage, new Vector2(160, textureSize.y-(286)), new Vector2(60, 60), null, false);
        is.setSlotType(SlotType.WEAPON);
        is.setDefaultImage(new Texture("Items/DefaultWeapon.png"));
        is.setHoverImage(new Texture("Items/highlight.png"));
        is.setImagealignment(Alignment.STRETCHED);
        defaultInventory.add(is);
    }

    void loadStatSlots(){
        int startX = (int)invenImage.getAbsolutePosition().x + 265, startY=(int)invenImage.getAbsolutePosition().y+146+14;
        for (int j = 0; j<5; j++){
            statSlots.add(new Rectangle(startX, textureSize.y-(startY+j*36), 106, 36));
        }
    }

    public void setToolbar(Toolbar toolbar){
        this.toolbar = toolbar;
        for (ItemSlot i : toolbar.defaultInventory){
            this.defaultInventory.add(i);
        }
    }

    public void setCharacter(Character c){
        this.character = c;
    }

    public void createFont() {
        font = Fonts.loadFont(Fonts.OPEN_SANS, 12, Color.WHITE, Color.BLACK);
    }

    public void update(CInputProcessor processor){
        boolean changedTooltipTrue = false;
        for (int i = 0; i<defaultInventory.size(); i++){
            ItemSlot is = defaultInventory.get(i);
            if (!is.isBlocked) {
                if (processor.mouseHovered(is.getAbsolutePosition().x, is.getAbsolutePosition().y, is.getSize().x, is.getSize().y)) {
                    is.setHovering(true);
                    if (is.getItem()!=null) {
                        changedTooltipTrue = true;
                        Item imanitem = is.getItem();
                        tooltip.update(imanitem);
                    }
                    if (processor.didMouseClick()) {
                        if (mouseItem != null) {
                            if (mouseItem.getType().equals(is.getSlotType()) || is.getSlotType().equals(SlotType.NORM)) {
                                Item temp = is.getItem();
                                if (i<12) {
                                    character.swapItemFromInventory(mouseItem, i);
                                } else if (i<21){
                                    character.swapItemFromGear(mouseItem, i);
                                } else {
                                    character.swapItemFromToolbar(mouseItem, i);
                                }
                                mouseItem = temp;
                            }
                        } else {
                            Item temp = is.getItem();
                            if (i<12) {
                                character.swapItemFromInventory(mouseItem, i);
                            } else if (i<21){
                                character.swapItemFromGear(mouseItem, i);
                            } else {
                                character.swapItemFromToolbar(mouseItem, i);
                            }
                            mouseItem = temp;
                        }
                    }
                } else {
                    is.setHovering(false);
                }
            }
        }
        mousePosition = processor.getMousePosition();
        tooltip.visible = changedTooltipTrue;
    }

    public void render(SpriteBatch batch, Item[] inven){
        if (visible) {
            if (character!=null) {
                for (int i = 0; i < inven.length; i++) {
                    defaultInventory.get(i).setItem(inven[i]);
                }
                renderBackground(batch);
                renderChildren(batch);
                TextureRegion temp = character.getImage();
                Rectangle tempRec = new Rectangle(invenImage.getAbsolutePosition().x+(invenImage.getSize().x-temp.getRegionWidth()*3)/2,
                        defaultInventory.get(defaultInventory.size()-1).getAbsolutePosition().y+40,
                        temp.getRegionWidth()*3, temp.getRegionHeight()*3);
                if (!(this instanceof Toolbar)) {
                    batch.begin();
                    batch.draw(temp, tempRec.x, tempRec.y, tempRec.width, tempRec.height);
                    Item[] gear = character.getInventory();
                    for (int i = 12; i<21; i++){
                        if (gear[i]!=null){
                            SpecialItem si = (SpecialItem)gear[i];
                            if (si.getCharacterOverlay()!=null){
                                TextureRegion t = si.getTextureRegion(character.getDirection(), 0);
                                batch.draw(t, tempRec.x, tempRec.y, tempRec.width, tempRec.height);
                            }
                        }
                    }
                    batch.end();
                    for (int i = 0; i < statSlots.size(); i++) {
                        String whatToWrite = "Joe";
                        String whatToWrite2 = "Sheila";
                        String modifiertype = "neutral";
                        if (i == 0) {
                            whatToWrite = character.getVitality() + "";
                            int val = (int)character.getBonusVitality();
                            if (val>0){modifiertype = "positive";}
                            else if (val<0){modifiertype = "negative";}
                            whatToWrite2 = val + "";
                        }
                        if (i == 1) {
                            whatToWrite = character.getAgility() + "";
                            int val = (int)character.getBonusAgility();
                            if (val>0){modifiertype = "positive";}
                            else if (val<0){modifiertype = "negative";}
                            whatToWrite2 = val + "";
                        }
                        if (i == 2) {
                            whatToWrite = character.getResistance() + "";
                            int val = (int)character.getBonusResistance();
                            if (val>0){modifiertype = "positive";}
                            else if (val<0){modifiertype = "negative";}
                            whatToWrite2 = val + "";
                        }
                        if (i == 3) {
                            whatToWrite = character.getStrength() + "";
                            int val = (int)character.getBonusStrength();
                            if (val>0){modifiertype = "positive";}
                            else if (val<0){modifiertype = "negative";}
                            whatToWrite2 = val + "";
                        }
                        if (i == 4) {
                            whatToWrite = character.getWisdom() + "";
                            int val = (int)character.getBonusWisdom();
                            if (val>0){modifiertype = "positive";}
                            else if (val<0){modifiertype = "negative";}
                            whatToWrite2 = val + "";
                        }
                        float width = font.getBounds(whatToWrite2).width;
                        batch.begin();
                        font.setColor(Color.WHITE);
                        font.draw(batch, whatToWrite, statSlots.get(i).x, statSlots.get(i).y);
                        if (modifiertype.equals("positive")){font.setColor(Color.GREEN);}
                        else if (modifiertype.equals("negative")){font.setColor(Color.RED);}
                        else {font.setColor(Color.WHITE);}
                        font.draw(batch, whatToWrite2, statSlots.get(i).x - width + 106, statSlots.get(i).y);
                        batch.end();
                    }
                    if (mouseItem != null) {
                        batch.begin();
                        batch.draw(mouseItem.image, mousePosition.x - mouseItem.image.getWidth() / 2, mousePosition.y - mouseItem.image.getHeight() / 2, mouseItem.image.getWidth(), mouseItem.image.getHeight());
                        batch.end();
                    } else {
                        tooltip.render(batch);
                    }
                } else {
                    batch.begin();
                    this.render(batch);
                    batch.end();
                }
            }
        }
    }

}
