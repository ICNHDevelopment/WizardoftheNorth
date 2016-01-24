package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 1/23/16.
 */
public class Battle {

    Texture background;

    public void create(BattleInfo battleInfo){
        background = new Texture(battleInfo.getBackFile());
    }

    public void update(CInputProcessor input){

    }

    public void render(SpriteBatch batch){
        batch.begin();

        batch.draw(background, 0, 0, Game.WIDTH(), Game.HEIGHT());

        batch.end();
    }
}
