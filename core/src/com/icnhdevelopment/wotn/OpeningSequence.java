package com.icnhdevelopment.wotn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icnhdevelopment.wotn.gui.Fonts;
import com.icnhdevelopment.wotn.handlers.GameState;

import java.util.ArrayList;

/**
 * Created by kyle on 1/20/16.
 */
public class OpeningSequence {

    final int NUMofLYMRICS = 3;

    class Lymric{
        Sound sound;
        String[] text;
        float time;

        float getLongestLineLength(BitmapFont font){
            float longestWid = 0;
            for (int i = 0; i<text.length; i++){
                float wid = font.getBounds(text[i]).width;
                longestWid = Math.max(longestWid, wid);
            }
            return longestWid;
        }
    }

    ArrayList<Lymric> lymrics;
    Lymric currentLymric;
    int currLym = 0;

    BitmapFont font;
    int fontSize = 32;

    long startTime;
    float alpha = 0f;

    String state = "fadein";

    public void start(){
        lymrics = new ArrayList<>();
        for (int i = 1; i<=NUMofLYMRICS; i++){
            Lymric l = new Lymric();
            l.sound = Gdx.audio.newSound(Gdx.files.internal("audio/lymrics/lymric" + i + ".wav"));
            l.text = Gdx.files.internal("audio/lymrics/lymric" + i + ".txt").readString().split("\n");
            l.time = Float.valueOf(l.text[l.text.length-1]);
            lymrics.add(l);
        }
        currentLymric = lymrics.get(currLym);
        font = Fonts.loadFont(Fonts.PRINCE_VALIANT, fontSize, Color.WHITE, Color.BLACK);
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
    }

    public void update(){
        if (state.equals("fadein")){
            alpha+=.01f;
            font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
            if (alpha>=1){
                font.setColor(Color.WHITE);
                state = "playsound";
                startTime = System.currentTimeMillis();
                currentLymric.sound.play();
            }
        }
        if (state.equals("playsound")){
            if ((System.currentTimeMillis()-startTime)>currentLymric.time*1000){
                state = "fadeout";
            }
        }
        if (state.equals("fadeout")){
            alpha-=.01f;
            font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
            if (alpha<=0){
                if (currLym<lymrics.size()-1){
                    alpha = 0;
                    font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
                    currLym++;
                    currentLymric = lymrics.get(currLym);
                    state = "fadein";
                }else{
                    Game.currentWorld.create("Sewer.tmx");
                    Game.GAME_STATE = GameState.WORLD;
                }
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        for (int i = 0; i<currentLymric.text.length-1; i++) {
            int bottom = (Game.HEIGHT()-(currentLymric.text.length-1)*(fontSize+2))/2;
            int y = bottom+((currentLymric.text.length-1)-(i+1))*(fontSize+2);
            float x = (Game.WIDTH() - currentLymric.getLongestLineLength(font))/2;
            font.draw(batch, currentLymric.text[i], x, y);
        }
        batch.end();
    }

}
