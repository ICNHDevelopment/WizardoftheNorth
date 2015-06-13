package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by kyle on 6/1/15.
 */
public class Button extends Label{

    private Image image;
    private Alignment imageAlignment = Alignment.SINGLE;
    Vector2 imagePosition, imageSize;

    public Button(Container pa, Vector2 pos, Vector2 sz, String text) {
        super(pa, pos, sz, text);

    }

    @Override
    public void render(SpriteBatch batch) {
        renderText(batch);
        renderChildren(batch);
    }

    public void Click() {

    }
}
