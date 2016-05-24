package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.players.Character;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuAttack extends BattleMenu {

    BattleMenuAction onlyAction;
    String choosingWhat = "action";

    Object action;
    Character doer;

    public BattleMenuAttack() {
        super();
        Texture actionImage = new Texture(Gdx.files.internal("ui/battle/attacks/slash.png"));
        Rectangle actionRec = new Rectangle(container.x + (container.width-container.height)/2,container.y, container.height, container.height);
        onlyAction = new BattleMenuAction(actionImage, actionRec);
    }

    @Override
    public void setParent(BattleMenu battleMenu) {
        parent = battleMenu;
    }

    @Override
    public void update(CInputProcessor input, Battle battle) {
        updateMenu(input, battle);
    }

    @Override
    public void updateMenu(CInputProcessor input, Battle battle) {
        if (choosingWhat.equals("action")) {
            if (onlyAction.update(input)) {
                action = "slash";
                doer = battle.currentTurn();
                choosingWhat = "target";
            }
        } else if (choosingWhat.equals("target")){
            if (input.didMouseClick()) {
                for (Character c : battle.getAntagonists()) {
                    Rectangle cRec = new Rectangle(c.getPosition().x, c.getPosition().y, c.getSize().x, c.getSize().y);
                    Rectangle mRec = new Rectangle(input.getMousePosition().x, input.getMousePosition().y, 1, 1);
                    if (cRec.overlaps(mRec)){
                        battle.setAction("slash", true, battle.currentTurn(), c);
                        BattleMenuMain.choseAction = true;
                    }
                }
            }
        }
        if (input.didMouseClick()){
            if (new Rectangle(input.getMousePosition().x, input.getMousePosition().y, 1, 1).overlaps(backRec)){
                choosingWhat = "action";
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
        onlyAction.render(batch);
        batch.draw(backButton, backRec.x, backRec.y, backRec.width, backRec.height);
    }
}