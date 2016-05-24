package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.battle.DamageText;
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
    BattleMenuAction[][] itemSlots;
    float itemSlotSideLength;
    int page = 0;
    Character character;

    Texture under, over;

    public BattleMenuItems(Character mainChar){
        character = mainChar;
        items = character.getBattleItems();
        itemSlotSideLength = (container.getHeight()-12)/2;
        int numWide = (int)((container.getWidth()-12)/itemSlotSideLength);
        itemSlots = new BattleMenuAction[numWide][2];
        loadRectangles();
        under = new Texture("ui/battle/orderUnderlay.png");
        over = new Texture("ui/battle/orderOverlay.png");
    }

    void loadRectangles(){
        int index = 0;
        for (int i = 0; i<itemSlots.length; i++){
            for (int j = 0; j<itemSlots[i].length; j++){
                Rectangle r = new Rectangle(container.x + 6 + (itemSlotSideLength * j), container.y + container.height - 6 - (itemSlotSideLength * (j + 1)), itemSlotSideLength, itemSlotSideLength);
                Item it;
                if (index<items.size()) {
                    it = items.get(index);
                    itemSlots[i][j] = new BattleMenuAction(it.image, r);
                } else {
                    itemSlots[i][j] = new BattleMenuAction(null, r);
                }
                index++;
            }
        }

    }

    @Override
    public void setParent(BattleMenu battleMenu) {
        this.parent = battleMenu;
    }

    @Override
    public void update(CInputProcessor input, Battle battle) {
        updateMenu(input, battle);
    }

    @Override
    public void updateMenu(CInputProcessor input, Battle battle) {
        int width = itemSlots.length, height = itemSlots[0].length;
        for (int i = (page*width*height); i<width*height; i++){
            if (i<items.size()){
                BattleMenuAction it = itemSlots[i/width][i%width];
                if (it.update(input)){
                    items.get(i).performFunction(character);
                    DamageText dt = new DamageText(items.get(i).getValue()+"", new Vector2(character.getPosition().x, character.getPosition().y + character.getSize().y), -1);
                    dt.setColor(Color.GREEN);
                    battle.addDamageText(dt);
                    items.remove(i);
                    loadRectangles();
                    battle.setAction("consume", true, battle.currentTurn(), battle.currentTurn());
                    BattleMenuMain.choseAction = true;
                }
            }
        }
        if (input.didMouseClick()){
            if (new Rectangle(input.getMousePosition().x, input.getMousePosition().y, 1, 1).overlaps(backRec)){
                BattleMenuMain.current = parent;
            }
        }
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
                BattleMenuAction it = itemSlots[i/width][i%width];
                Rectangle temp = it.rectangle;
                batch.draw(under, temp.x, temp.y, temp.width, temp.height);
                it.render(batch);
                batch.draw(over, temp.x, temp.y, temp.width, temp.height);
            }
        }
        batch.draw(backButton, backRec.x, backRec.y);
    }
}
