package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Fonts;

/**
 * Created by kyle on 5/23/16.
 */
public class DamageText {

    String txt;
    Color color = new Color(Color.RED);
    int direction;
    Vector2 position;
    BitmapFont font;
    Vector2 velocity;

    public DamageText(String damage, Vector2 pos, int dir){
        txt = damage;
        direction = dir;
        position = pos;
        font = Fonts.loadFont(Fonts.OPEN_SANS, 14, color, color);
        velocity = new Vector2(2*dir, 5);
    }

    public void setColor(Color col){
        font.setColor(col);
    }

    public boolean update(){
        velocity.y -= .5;
        position.x += velocity.x;
        position.y += velocity.y;
        if (position.y<0) return true;
        return false;
    }

    public void render(SpriteBatch batch){
        font.draw(batch, txt, position.x, position.y);
    }
}
