package com.icnhdevelopment.wotn.battle.battlegui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.gui.Fonts;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

/**
 * Created by kyle on 1/25/16.
 */
public class BattleMenuButton {

    String name;
    Rectangle rectangle;
    Texture image;
    Texture imageH;
    BitmapFont font;
    boolean drawName;
    boolean hover = false;
    BattleMenu switchTo;

    public BattleMenuButton(String name, Rectangle rectangle, BattleMenu switchTo){
        this.name = name;
        this.rectangle = rectangle;
        this.switchTo = switchTo;
        image = new Texture(Gdx.files.internal("ui/battle/button.png"));
        imageH = new Texture(Gdx.files.internal("ui/battle/buttonH.png"));
        font = Fonts.loadFont(Fonts.OPEN_SANS, 22, Color.WHITE, Color.BLACK);
        drawName = true;
    }

    public void update(CInputProcessor processor){
        if (processor.mouseHovered(rectangle.x, rectangle.y, rectangle.width, rectangle.height)){
            hover = true;
            if (processor.didMouseClick()){
                BattleMenuMain.current = switchTo;
            }
        }else{
            hover = false;
        }
    }

    public void render(SpriteBatch batch){
        batch.draw(hover?imageH:image, rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        if (drawName){
            float width = font.getBounds(name).width;
            font.draw(batch, name, rectangle.x+(rectangle.width-width)/2, rectangle.y+(rectangle.getHeight()-24)/2+22);
        }
    }
}
