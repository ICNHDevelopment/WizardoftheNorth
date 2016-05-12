package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 5/12/16.
 */
public class BattleMenuAction {

    Rectangle rectangle;
    Texture image;

    public BattleMenuAction(Texture im, Rectangle rec){
        image = im;
        rectangle = rec;
    }

    public boolean update(CInputProcessor processor){
        if (processor.mouseHovered(rectangle.x, rectangle.y, rectangle.width, rectangle.height)){
            if (processor.didMouseClick()){
                return true;
            }
        }

        return false;
    }

    public void render(SpriteBatch batch){
        batch.draw(image, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }
}
