package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Alignment;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.ImageLabel;

/**
 * Created by kyle on 1/15/16.
 */
public class Hud extends Container {

    ImageLabel topleftHud;
    ImageLabel experienceHud;

    public Hud(){
        super();
        create();
    }

    void create(){
        Texture temp = new Texture("ui/hud/HUD.png");
        topleftHud = new ImageLabel(this, new Vector2(0, Game.HEIGHT()-temp.getHeight()*2), new Vector2(temp.getWidth()*2, temp.getHeight()*2), temp);
        topleftHud.setImagealignment(Alignment.STRETCHED);
    }

    public void render(SpriteBatch batch){

    }
}
