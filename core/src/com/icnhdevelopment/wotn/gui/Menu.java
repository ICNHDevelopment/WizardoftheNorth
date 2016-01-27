package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.TextConverter;

/**
 * Created by kyle on 9/7/15.
 */
public class Menu {

    public Container mainContainer;

    //Main Menu
    //Albert replace this code with the xml loading code
    //(all the important properties have get/set methods)
    public void init(String file){
        mainContainer = TextConverter.Array_To_Container(TextConverter.Text_To_Array(Gdx.files.internal(file)));
    }

    public void update(CInputProcessor inputProcessor){
        mainContainer.updateChildren(inputProcessor);
    }

    public void render(SpriteBatch batch){
        mainContainer.renderBackground(batch);
        mainContainer.render(batch);
    }
}
