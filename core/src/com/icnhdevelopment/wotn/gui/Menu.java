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
    public void init(){
        /*
        mainContainer = new Container();
        Label l = new Label(mainContainer, new Vector2(0, Game.HEIGHT()-40), new Vector2(Game.WIDTH(), 40), "The Wizard of the North");
        l.setFontsize(40);
        l.setColor(Color.WHITE);
        l.setBordercolor(Color.RED);
        l.sethalignment(Alignment.CENTER);
        l.setvalignment(Alignment.MIDDLE);
        l.setBackcolor(Color.BLUE);
        l.setUsefontsize(false);
        l.createFont();
        Label b = new Label(mainContainer, new Vector2(0, 0), new Vector2(4, 4), "Exit") {
            @Override
            public void Click() {
                Game.EXIT();
            }
        };
        b.setFontsize(30);
        b.setColor(Color.BLACK);
        b.setBordercolor(Color.GREEN);
        b.setBackcolor(Color.YELLOW);
        //b.setValignment(Alignment.MIDDLE);
        //b.setHalignment(Alignment.CENTER);
        b.setUsefontsize(true);
        b.createFont();
        mainContainer.buttons.add(b);
        ImageLabel il = new ImageLabel(mainContainer, new Vector2(30, 30), new Vector2(224*2, 98*2), new Texture(Gdx.files.internal("ui/PlayBTN.png"))){
            @Override
            public void Click() {
                Game.currentWorld.create("Sewer.tmx");
                Game.GAME_STATE = GameState.WORLD;
            }
        };
        il.setImagealignment(Alignment.STRETCHED);
        il.setHoverImage(new Texture("ui/PlayBTNH.png"));
        mainContainer.buttons.add(il);
        */
        mainContainer = TextConverter.Array_To_Container(TextConverter.Text_To_Array("ui/Menus/MNUMain.txt"));
    }

    public void update(CInputProcessor inputProcessor){
        mainContainer.updateChildren(inputProcessor);
    }

    public void render(SpriteBatch batch){
        mainContainer.renderBackground(batch);
        mainContainer.render(batch);
    }
}
