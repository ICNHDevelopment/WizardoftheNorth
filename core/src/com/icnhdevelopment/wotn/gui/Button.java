package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by kyle on 6/1/15.
 */
public class Button extends Label implements ImageDisplay {

    private Image image;
    private Alignment imageVAlignment = Alignment.BOTTOM, imageHAlignment = Alignment.TOP;

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

    @Override
    public void setImage(Image i) {
        this.image = i;
    }

    @Override
    public void setImageVAlignment(Alignment a) {
        this.imageVAlignment = a;
    }

    @Override
    public void setImageHAlignment(Alignment a) {
        this.imageHAlignment = a;
    }
}
