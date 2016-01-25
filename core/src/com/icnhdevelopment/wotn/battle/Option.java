package com.icnhdevelopment.wotn.battle;

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
public class Option {

    String name;
    Rectangle rectangle;
    Texture image;
    Texture imageH;
    BitmapFont font;
    boolean drawName;
    boolean hover = false;
    String type;
    int changeMenu = -1;

    public Option(String name, Rectangle rectangle){
        this.name = name;
        this.rectangle = rectangle;
        font = Fonts.loadFont(Fonts.OPEN_SANS, 22, Color.WHITE, Color.BLACK);
        drawName = true;
    }

    public Option(String name, Rectangle rectangle, Texture texture, Texture h, boolean dn){
        this(name, rectangle);
        this.image = texture;
        this.imageH = h;
        drawName = dn;
    }

    public void update(CInputProcessor processor){
        if (processor.mouseHovered(rectangle.x, rectangle.y, rectangle.width, rectangle.height)){
            hover = true;
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

    public int getChangeMenu() {
        return changeMenu;
    }

    public void setChangeMenu(int changeMenu) {
        this.changeMenu = changeMenu;
    }
}
