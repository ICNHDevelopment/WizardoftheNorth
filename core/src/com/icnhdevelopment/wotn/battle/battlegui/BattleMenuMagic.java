package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuMagic extends BattleMenu {

    @Override
    public void setParent(BattleMenu battleMenu) {

    }

    @Override
    public void update(CInputProcessor input, Battle battle) {

    }

    @Override
    public void updateMenu(CInputProcessor input, Battle battle) {

        if (input.didMouseClick()){
            if (new Rectangle(input.getMousePosition().x, input.getMousePosition().y, 1, 1).overlaps(backRec)){
                BattleMenuMain.current = parent;
            }
        }
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    @Override
    public void renderMenu(SpriteBatch batch) {

        batch.draw(backButton, backRec.x, backRec.y, backRec.width, backRec.height);
    }
}
