package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.players.Sprite;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuMain extends BattleMenu {

    BattleMenuButton attack, support, items, magic;
    static BattleMenu current;

    public BattleMenuMain() {
        super();
        current = this;
        attack = new BattleMenuButton("Attack", buttonSpots[0], new BattleMenuAttack());
        items = new BattleMenuButton("Items", buttonSpots[1], new BattleMenuItems());
        support = new BattleMenuButton("Support", buttonSpots[2], new BattleMenuSupport());
        magic = new BattleMenuButton("Magic", buttonSpots[3], new BattleMenuMagic());
    }

    public void update(CInputProcessor input){
        attack.update(input);
        items.update(input);
        support.update(input);
        magic.update(input);
    }

    public void render(SpriteBatch batch){
        current.renderMenu(batch);
    }

    public void renderMenu(SpriteBatch batch){
        attack.render(batch);
        items.render(batch);
        support.render(batch);
        magic.render(batch);
    }
}
