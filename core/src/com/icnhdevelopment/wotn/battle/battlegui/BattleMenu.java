package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 5/12/16.
 */
public abstract class BattleMenu {

    Rectangle container = new Rectangle(369, 6, 543, 228);

    public BattleMenu(){
    }

    public abstract void update(CInputProcessor input, Battle battle);

    public abstract void updateMenu(CInputProcessor input, Battle battle);

    public abstract void render(SpriteBatch batch);

    public abstract void renderMenu(SpriteBatch batch);
}
