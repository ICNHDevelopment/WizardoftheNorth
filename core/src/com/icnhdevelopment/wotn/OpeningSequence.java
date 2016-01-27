package com.icnhdevelopment.wotn;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.gui.Fonts;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.GameState;

import java.util.ArrayList;

/**
 * Created by kyle on 1/20/16.
 */
public class OpeningSequence {

    final int NUMofLYMRICS = 5;

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
    long holdStart = 0L;

    String state = "fadein";
    String stage = "lymric";

    Sound starwars;
    String[] starwarsText;
    BitmapFont starWarsFont;
    float textY = 0;

    public void start(){
        Game.soundHandler.PlaySound(Gdx.audio.newSound(Gdx.files.internal("audio/openingMusic.ogg")), .5f);
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

        starwars = Gdx.audio.newSound(Gdx.files.internal("audio/openingMusic2.ogg"));
        starwarsText = Gdx.files.internal("audio/lymrics/starwars.txt").readString().split("\n");
        starWarsFont = Fonts.loadFont(Fonts.STAR_WARS, 42, Color.YELLOW, Color.BLACK);
    }

    public void update(CInputProcessor inputProcessor){
        if (inputProcessor.isKeyDown(Input.Keys.SPACE)){
            if (holdStart==0){
                holdStart = System.currentTimeMillis();
            } else if (System.currentTimeMillis()-holdStart>1500){
                currentLymric.sound.stop();
                goToWorld();
            }
        } else {
            holdStart = 0;
        }
        if (stage.equals("lymric")) {
            if (state.equals("fadein")) {
                alpha += .01f;
                font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
                if (alpha >= 1) {
                    font.setColor(Color.WHITE);
                    state = "playsound";
                    startTime = System.currentTimeMillis();
                    currentLymric.sound.play();
                }
            }
            if (state.equals("playsound")) {
                if ((System.currentTimeMillis() - startTime) > currentLymric.time * 1000) {
                    state = "fadeout";
                }
            }
            if (state.equals("fadeout")) {
                alpha -= .01f;
                font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
                if (alpha <= 0) {
                    if (currLym < lymrics.size() - 1) {
                        alpha = 0;
                        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
                        currLym++;
                        currentLymric = lymrics.get(currLym);
                        state = "fadein";
                    } else {
                        stage = "starwars";
                        alpha = 0;
                        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
                        Game.soundHandler.PlaySound(starwars);
                    }
                }
            }
        } else if (stage.equals("starwars")){
            textY+=.45;
            if (textY>Game.HEIGHT()+44*starwarsText.length){
                goToWorld();
            }
        }
    }

    void goToWorld(){
        alpha = 0;
        font.setColor(font.getColor().r, font.getColor().g, font.getColor().b, alpha);
        Game.currentWorld.create("world/Levels/Sewer/Sewer.tmx");
        Game.GAME_STATE = GameState.WORLD;
    }

    public void render(SpriteBatch batch){
        if (stage.equals("lymric")) {
            batch.begin();
            for (int i = 0; i < currentLymric.text.length - 1; i++) {
                int bottom = (Game.HEIGHT() - (currentLymric.text.length - 1) * (fontSize + 2)) / 2;
                int y = bottom + ((currentLymric.text.length - 1) - i) * (fontSize + 2);
                float x = (Game.WIDTH() - currentLymric.getLongestLineLength(font)) / 2;
                font.draw(batch, currentLymric.text[i], x, y);
            }
            if (holdStart > 0) {
                font.draw(batch, "Skip", 2, (4 + fontSize));
                Rectangle skipBar = new Rectangle(2, (10 + fontSize), font.getBounds("Skip").width, 8);
                Texture bar = new Texture("ui/hud/ExperienceMeter.png");
                Color c = batch.getColor();
                Color b = Color.GRAY;
                batch.setColor(b.r, b.g, b.b, 1);
                batch.draw(bar, skipBar.x, skipBar.y, Math.min((System.currentTimeMillis() - holdStart) / 1500.0f * skipBar.width, skipBar.width), skipBar.height);
                batch.setColor(c);
            }
            batch.end();
        } else {
            batch.begin();
            for (int i = 0; i<starwarsText.length; i++){
                float top = textY;
                float y = top - (44)*i;
                float x = (Game.WIDTH() - starWarsFont.getBounds("drinking with your friend, Name.").width) / 2;
                starWarsFont.draw(batch, starwarsText[i], x, y);
            }
            batch.end();
        }
    }

}
