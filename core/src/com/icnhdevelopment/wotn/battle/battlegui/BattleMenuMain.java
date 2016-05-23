package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.players.Character;
import com.icnhdevelopment.wotn.players.Sprite;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuMain extends BattleMenu {

    BattleMenuButton attack, support, items, magic;
    static BattleMenu current;
    public static boolean choseAction = false;
    Rectangle[] buttonSpots = new Rectangle[4];

    public BattleMenuMain(Character main) {
        super();
        buttonSpots[0] = new Rectangle(container.x+(container.width-(182*2))/2-4, container.y+container.height-(6+76), 182, 76);
        buttonSpots[1] = new Rectangle(container.x+(container.width-(182*2))/2+186, container.y+container.height-(6+76), 182, 76);
        buttonSpots[2] = new Rectangle(container.x+(container.width-(182*2))/2-4, container.y+container.height-(12+152), 182, 76);
        buttonSpots[3] = new Rectangle(container.x+(container.width-(182*2))/2+186, container.y+container.height-(12+152), 182, 76);
        current = this;
        BattleMenu battleMenu = new BattleMenuAttack();
        battleMenu.setParent(this);
        attack = new BattleMenuButton("Attack", buttonSpots[0], battleMenu);
        battleMenu = new BattleMenuItems(main);
        battleMenu.setParent(this);
        items = new BattleMenuButton("Items", buttonSpots[1], battleMenu);
        battleMenu = new BattleMenuSupport();
        battleMenu.setParent(this);
        support = new BattleMenuButton("Support", buttonSpots[2], battleMenu);
        battleMenu = new BattleMenuMagic();
        battleMenu.setParent(this);
        magic = new BattleMenuButton("Magic", buttonSpots[3], battleMenu);
    }

    @Override
    public void setParent(BattleMenu battleMenu) {

    }

    public void update(CInputProcessor input, Battle battle){
        current.updateMenu(input, battle);
    }

    @Override
    public void updateMenu(CInputProcessor input, Battle battle) {
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
