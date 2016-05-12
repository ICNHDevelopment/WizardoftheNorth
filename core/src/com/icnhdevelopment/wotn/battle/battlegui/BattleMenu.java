package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 5/12/16.
 */
public abstract class BattleMenu {

    Rectangle container = new Rectangle(369, 6, 543, 228);
    Rectangle[] buttonSpots = new Rectangle[4];

    public BattleMenu(){
        buttonSpots[0] = new Rectangle(container.x+(container.width-(182*2))/2-4, container.y+container.height-(6+76), 182, 76);
        buttonSpots[1] = new Rectangle(container.x+(container.width-(182*2))/2+186, container.y+container.height-(6+76), 182, 76);
        buttonSpots[2] = new Rectangle(container.x+(container.width-(182*2))/2-4, container.y+container.height-(12+152), 182, 76);
        buttonSpots[3] = new Rectangle(container.x+(container.width-(182*2))/2+186, container.y+container.height-(12+152), 182, 76);
    }

    public abstract void update(CInputProcessor input);

    public abstract void render(SpriteBatch batch);

    public abstract void renderMenu(SpriteBatch batch);
}
