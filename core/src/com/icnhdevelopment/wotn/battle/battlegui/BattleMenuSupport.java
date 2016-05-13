package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuSupport extends BattleMenu {

    BattleMenuAction focus, protect;

    public BattleMenuSupport() {
        super();
        Texture actionImage = new Texture(Gdx.files.internal("ui/battle/supports/focus.png"));
        Rectangle actionRec = new Rectangle(container.x + (container.width/2-container.height), container.y, container.height, container.height);
        focus = new BattleMenuAction(actionImage, actionRec);
        actionImage = new Texture(Gdx.files.internal("ui/battle/supports/protect.png"));
        actionRec = new Rectangle(container.x + (container.width)/2,container.y, container.height, container.height);
        protect = new BattleMenuAction(actionImage, actionRec);
    }

    @Override
    public void update(CInputProcessor input, Battle battle) {
        updateMenu(input, battle);
    }

    @Override
    public void updateMenu(CInputProcessor input, Battle battle) {
        if (focus.update(input)){
            battle.setAction("focus", battle.currentTurn(), battle.currentTurn());
            BattleMenuMain.choseAction = true;
        } else if (protect.update(input)){
            battle.setAction("protect", battle.currentTurn(), battle.currentTurn());
            BattleMenuMain.choseAction = true;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        renderMenu(batch);
    }

    @Override
    public void renderMenu(SpriteBatch batch) {
        focus.render(batch);
        protect.render(batch);
    }
}
