package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.GameState;
import com.icnhdevelopment.wotn.world.World;

import java.util.Date;
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
        Label l = new Label(mainContainer, new Vector2(0, Game.HEIGHT()-44), new Vector2(Game.WIDTH(), 40), "The Wizard of the North");
        l.setFontSize(40);
        l.setColor(Color.WHITE);
        l.setBorderColor(Color.RED);
        l.sethAlignment(Alignment.CENTER);
        l.setBackColor(Color.BLUE);
        l.createFont(false);
        Label b = new Label(mainContainer, new Vector2(0, 0), new Vector2(4, 4), "Exit") {
            @Override
            public void Click() {
                Game.EXIT();
            }
        };
        b.setFontSize(30);
        b.setColor(Color.BLACK);
        b.setBorderColor(Color.GREEN);
        b.setBackColor(Color.YELLOW);
        //b.setvAlignment(Alignment.MIDDLE);
        //b.sethAlignment(Alignment.CENTER);
        b.createFont(true);
        mainContainer.buttons.add(b);
        ImageLabel il = new ImageLabel(mainContainer, new Vector2(30, 30), new Vector2(224*3, 98*3), new Texture(Gdx.files.internal("ui/PlayBTN.png"))){
            @Override
            public void Click() {
                Game.currentWorld.create("Sewer.tmx");
                Game.GAME_STATE = GameState.WORLD;
            }
        };
        il.setImageAlignment(Alignment.STRETCHED);
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
        b.setBackColor(Color.ORANGE);
        b.sethAlignment(Alignment.CENTER);
        b.setvAlignment(Alignment.MIDDLE);
        b.createFont(true);
        mainContainer.buttons.add(b);
    }

    public void update(CInputProcessor inputProcessor){
        mainContainer.updateChildren(inputProcessor);
    }

    public void render(SpriteBatch batch){
        mainContainer.renderBackground(batch);
        mainContainer.render(batch);
    }
}
