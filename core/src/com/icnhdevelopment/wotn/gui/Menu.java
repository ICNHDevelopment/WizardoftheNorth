package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.GameState;
import com.icnhdevelopment.wotn.handlers.XMLConverter;

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
    public void init(){
        mainContainer = new Container();
        mainContainer = XMLConverter.XML_Converter(new File("core/assets/ui/Menus/MNUMain")).get(0);
        /*
        Label l = new Label(mainContainer, new Vector2(0, Game.HEIGHT()-44), new Vector2(Game.WIDTH(), 40), "The Wizard of the North");
        l.setFontsize(40);
        l.setColor(Color.WHITE);
        l.setBordercolor(Color.RED);
        l.sethalignment(Alignment.CENTER);
        l.setBackcolor(Color.BLUE);
        l.setUsefontsize(true);
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
        ImageLabel il = new ImageLabel(mainContainer, new Vector2(30, 30), new Vector2(224*3, 98*3), new Texture(Gdx.files.internal("ui/PlayBTN.png"))){
            @Override
            public void Click() {
                Game.currentWorld.create("Sewer.tmx");
                Game.GAME_STATE = GameState.WORLD;
            }
        };
        il.setImagealignment(Alignment.STRETCHED);
        mainContainer.buttons.add(il);
        b = new Label(il, new Vector2(10, 10), new Vector2(20, 20), "Shrink :D") {
            @Override
            public void Click() {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (mainContainer.getSize().x > Game.WIDTH()/3) {
                            mainContainer.getAbsoluteParent().resize(mainContainer.getSize().x-9, mainContainer.getSize().y);
                        }
                        if (mainContainer.getSize().y > Game.HEIGHT()/3) {
                            mainContainer.getAbsoluteParent().resize(mainContainer.getSize().x, mainContainer.getSize().y-9);
                        }
                        if (mainContainer.getSize().x <= Game.WIDTH()/3 && mainContainer.getSize().y <= Game.HEIGHT()/3) {
                            this.cancel();
                        }
                    }
                }, 20);
            }
        };
        b.setBackcolor(Color.ORANGE);
        b.sethalignment(Alignment.CENTER);
        b.setvalignment(Alignment.MIDDLE);
        b.setUsefontsize(true);
        b.createFont();
        mainContainer.buttons.add(b);
        */
    }

    public void update(CInputProcessor inputProcessor){
        mainContainer.updateChildren(inputProcessor);
    }

    public void render(SpriteBatch batch){
        mainContainer.renderBackground(batch);
        mainContainer.render(batch);
    }
}
