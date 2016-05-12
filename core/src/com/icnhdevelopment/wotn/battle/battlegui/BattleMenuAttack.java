package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuAttack extends BattleMenu {

    BattleMenuAction onlyAction;

    public BattleMenuAttack() {
        super();
        Texture actionImage = new Texture(Gdx.files.internal("ui/battle/attacks/slash.png"));
        Rectangle actionRec = new Rectangle(container.x + (container.width-container.height)/2,container.y, container.height, container.height);
        onlyAction = new BattleMenuAction(actionImage, actionRec);
    }

    @Override
    public void update(CInputProcessor input) {

    }

    @Override
    public void render(SpriteBatch batch) {
        renderMenu(batch);
    }

    @Override
    public void renderMenu(SpriteBatch batch) {
        onlyAction.render(batch);
    }
}
