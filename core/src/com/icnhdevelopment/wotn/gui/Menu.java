package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.GameState;
import com.icnhdevelopment.wotn.handlers.TextConverter;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

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
