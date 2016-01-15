package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Alignment;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.Fonts;
import com.icnhdevelopment.wotn.gui.ImageLabel;
import com.icnhdevelopment.wotn.players.Character;

/**
 * Created by kyle on 1/15/16.
 */
public class Hud extends Container {

    ImageLabel topleftHud;
    ImageLabel experienceHud;
    Rectangle vitR, wisR, expR, levelDisplay;
    Texture vitT, wisT, expT;

    Character character;

    BitmapFont font;

    public Hud(Character c){
        super();
        create();
        character = c;
    }

    void create(){
        Texture temp = new Texture("ui/hud/HUD.png");
        topleftHud = new ImageLabel(this, new Vector2(0, Game.HEIGHT()-temp.getHeight()*2), new Vector2(temp.getWidth()*2, temp.getHeight()*2), temp);
        topleftHud.setImagealignment(Alignment.STRETCHED);
        vitR = new Rectangle(32*2, topleftHud.getAbsolutePosition().y + temp.getHeight()*2-16, 163*2, 7*2);
        wisR = new Rectangle(32*2, topleftHud.getAbsolutePosition().y + temp.getHeight()*2-32, 163*2, 7*2);
        temp = new Texture("ui/hud/experience.png");
        experienceHud = new ImageLabel(this, Vector2.Zero, new Vector2(temp.getWidth()*3, temp.getHeight()*3), temp);
        experienceHud.setImagealignment(Alignment.STRETCHED);
        expR = new Rectangle(0, 18, temp.getWidth()*3, 9);
        levelDisplay = new Rectangle(210*3, 5*3, 9*3, 4*3);

        vitT = new Texture("ui/hud/VitalityMeter.png");
        wisT = new Texture("ui/hud/WisdomMeter.png");
        expT = new Texture("ui/hud/ExperienceMeter.png");

        createFont();
    }

    public void createFont() {
        font = Fonts.loadFont(Fonts.OPEN_SANS, 10, Color.WHITE, Color.BLACK);
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(expT, expR.x, expR.y, expR.getWidth(), expR.getHeight());
        batch.end();

        super.render(batch);

        batch.begin();
        batch.draw(vitT, vitR.x, vitR.y, ((float)character.getCurrentVitality()/(float)character.getVitality())*vitR.getWidth(), vitR.getHeight());
        batch.draw(wisT, wisR.x, wisR.y, ((float)character.getCurrentWisdom()/(float)character.getWisdom())*wisR.getWidth(), wisR.getHeight());
        String level = character.getLevel() + "";
        float width = font.getBounds(level).width;
        float height = font.getBounds(level).height;
        font.draw(batch, level, levelDisplay.x+(levelDisplay.width-width)/2, levelDisplay.y+height/3+(levelDisplay.height-height)/2*3);
        batch.end();
    }
}
