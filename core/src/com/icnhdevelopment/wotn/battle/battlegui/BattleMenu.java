package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 5/12/16.
 */
public abstract class BattleMenu {

    Rectangle container;
    BattleMenu parent;
    Texture backButton;
    Rectangle backRec;

    public BattleMenu(){
        container = new Rectangle(369, 6, 543, 228);
        backButton = new Texture("ui/battle/back.png");
        backRec = new Rectangle(container.x + container.width - 30, container.y + container.height - 30, 30, 30);
    }

    public abstract void setParent(BattleMenu battleMenu);

    public abstract void update(CInputProcessor input, Battle battle);

    public abstract void updateMenu(CInputProcessor input, Battle battle);

    public abstract void render(SpriteBatch batch);

    public abstract void renderMenu(SpriteBatch batch);
}
