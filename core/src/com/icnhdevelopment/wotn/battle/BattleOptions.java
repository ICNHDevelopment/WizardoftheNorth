package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.gui.special.Tooltip;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.players.Character;

import java.util.ArrayList;

/**
 * Created by kyle on 1/25/16.
 */
public class BattleOptions {

    Tooltip tooltip;
    boolean hoverOption = false;
    int currentMenu = 0;
    ArrayList<ArrayList> menus;
    Rectangle position;

    public void create(Character character, Rectangle pos){
        this.position = pos;
        menus = new ArrayList<>();
        ArrayList<Option> o = new ArrayList<>();
        Rectangle temp = new Rectangle(pos.x+(pos.width-(182*2+6))/2+3, pos.y+pos.height-(6+76), 182, 76);
        Texture tempT = new Texture("ui/battle/button.png");
        Texture tempH = new Texture("ui/battle/buttonH.png");
        Option tempo = new Option("Attacks", temp, tempT, tempH, true);
        tempo.setChangeMenu(1);
        tempo.type = "changemenu";
        o.add(tempo);
        temp = new Rectangle(pos.x+(pos.width-(182*2+6))/2+188, pos.y+pos.height-(6+76), 182, 76);
        tempo = new Option("Items", temp, tempT, tempH, true);
        tempo.setChangeMenu(2);
        tempo.type = "changemenu";
        o.add(tempo);
        menus.add(o);
        o = new ArrayList<Option>();
    }

    public void update(CInputProcessor processor){
        //noinspection unchecked
        for (ArrayList<Option> o : menus){
            for (Option option : o){
                option.update(processor);
            }
        }
    }

    public void render(SpriteBatch batch){
        //noinspection unchecked
        for (ArrayList<Option> o : menus){
            for (Option option : o){
                option.render(batch);
            }
        }
    }
}
