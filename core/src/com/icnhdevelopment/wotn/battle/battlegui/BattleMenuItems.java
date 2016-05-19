package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.items.BattleItem;
import com.icnhdevelopment.wotn.players.Character;

import java.util.ArrayList;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuItems extends BattleMenu {

    ArrayList<BattleItem> items;

    public BattleMenuItems(Character mainChar){
        items = mainChar.getBattleItems();
    }

    @Override
    public void update(CInputProcessor input, Battle battle) {

    }

    @Override
    public void updateMenu(CInputProcessor input, Battle battle) {

    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void renderMenu(SpriteBatch batch) {

    }
}
