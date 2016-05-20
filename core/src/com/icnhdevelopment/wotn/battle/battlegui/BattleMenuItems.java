package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.items.BattleItem;
import com.icnhdevelopment.wotn.items.Item;
import com.icnhdevelopment.wotn.players.Character;

import java.util.ArrayList;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuItems extends BattleMenu {

    ArrayList<BattleItem> items;
    Rectangle[][] itemSlots;
    float itemSlotSideLength;
    int page = 0;

    Texture under, over;

    public BattleMenuItems(Character mainChar){
        items = mainChar.getBattleItems();
        itemSlotSideLength = (container.getHeight()-12)/2;
        int numWide = (int)((container.getWidth()-12)/itemSlotSideLength);
        itemSlots = new Rectangle[numWide][2];
        loadRectangles();
        under = new Texture("ui/battle/orderUnderlay.png");
        over = new Texture("ui/battle/orderOverlay.png");
    }

    void loadRectangles(){
        for (int i = 0; i<itemSlots.length; i++){
            for (int j = 0; j<itemSlots[i].length; j++){
                itemSlots[i][j] = new Rectangle(container.x+6 + (itemSlotSideLength*j), container.y+6 + (itemSlotSideLength*j), itemSlotSideLength, itemSlotSideLength);
            }
        }
    }

    @Override
    public void update(CInputProcessor input, Battle battle) {

    }

    @Override
    public void updateMenu(CInputProcessor input, Battle battle) {

    }

    @Override
    public void render(SpriteBatch batch) {
        renderMenu(batch);
    }

    @Override
    public void renderMenu(SpriteBatch batch) {
        int width = itemSlots.length, height = itemSlots[0].length;
        for (int i = (page*width*height); i<width*height; i++){
            if (i<items.size()){
                Rectangle temp = itemSlots[i/width][i%width];
                Item tempIt = items.get(i);
                batch.draw(under, temp.x, temp.y, temp.width, temp.height);
                batch.draw(tempIt.image, temp.x, temp.y, temp.width, temp.height);
                batch.draw(over, temp.x, temp.y, temp.width, temp.height);
            }
        }
    }
}
