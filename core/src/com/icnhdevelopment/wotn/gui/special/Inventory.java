package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.ImageLabel;

/**
 * Created by kyle on 1/13/16.
 */
public class Inventory extends Container {

    static double invenScaleX = 192/1280.0, invenScaleY = 288/720.0;

    public Inventory(){
        super();
        create();
    }

    void create(){
        ImageLabel il = new ImageLabel(this, new Vector2(30, 30), new Vector2(224*3, 98*3), new Texture(Gdx.files.internal("Inventory.png")));
        this.children.add(il);
    }


}
