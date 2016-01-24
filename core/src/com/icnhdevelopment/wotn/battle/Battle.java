package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.GameState;
import com.icnhdevelopment.wotn.players.Character;
import com.icnhdevelopment.wotn.world.World;

import java.util.ArrayList;

/**
 * Created by kyle on 1/23/16.
 */
public class Battle {

    Texture battleTransition;
    Texture background;
    String state = "fadein";
    int battleStage = 7;
    Vector2 charPos;
    Character enemy;
    World world;

    int TICK = 1;
    int SCALE = 3;

    ArrayList<Rectangle> protPositions;
    ArrayList<Rectangle> antPositions;
    ArrayList<Character> protSide;
    ArrayList<Character> antSide;
    ArrayList<Character> fightOrder;
    int whoseturn = 0;

    public void create(BattleInfo battleInfo){
        background = new Texture(battleInfo.getBackFile());
        battleTransition = battleInfo.battleTex;
        protSide = battleInfo.getProtSide();
        antSide = battleInfo.getAntSide();
        charPos = battleInfo.getCharacterWorldPosition();
        enemy = battleInfo.getEnemy();
        world = battleInfo.getWorld();
        loadPositions();
        setPositions();
        setBattleOrder();
    }

    void loadPositions(){
        protPositions = new ArrayList<>();
        protPositions.add(new Rectangle(387, 351, 90, 90));
        protPositions.add(new Rectangle(387, 447, 90, 90));
        protPositions.add(new Rectangle(387, 255, 90, 90));
        protPositions.add(new Rectangle(195, 351, 90, 90));
        protPositions.add(new Rectangle(195, 447, 90, 90));
        protPositions.add(new Rectangle(195, 255, 90, 90));
        antPositions = new ArrayList<>();
        antPositions.add(new Rectangle(867, 351, 90, 90));
        antPositions.add(new Rectangle(867, 447, 90, 90));
        antPositions.add(new Rectangle(867, 255, 90, 90));
        antPositions.add(new Rectangle(1059, 351, 90, 90));
        antPositions.add(new Rectangle(1059, 447, 90, 90));
        antPositions.add(new Rectangle(1059, 255, 90, 90));
    }

    void setPositions(){
        for (int i = 0; i<protSide.size(); i++){
            Character c = protSide.get(i);
            Rectangle r = protPositions.get(i);
            Rectangle hit = c.getHitBox();
            float x = (r.x + (r.width-hit.width)/2);
            float y = (r.y + (r.height-hit.height)/2);
            c.moveByHitBoxToPosition(new Vector2(x, y));
        }
        for (int i = 0; i<antSide.size(); i++){
            Character c = antSide.get(i);
            Rectangle r = antPositions.get(i);
            Rectangle hit = c.getHitBox();
            float x = (r.x + (r.width-hit.width)/2);
            float y = (r.y + (r.height-hit.height)/2);
            c.moveByHitBoxToPosition(new Vector2(x, y));
        }
    }

    void setBattleOrder(){
        fightOrder = new ArrayList<>();
        fightOrder.addAll(protSide);
        fightOrder.addAll(antSide);
        boolean changed = true;
        while (changed){
            changed = false;
            for (int i = 0; i<fightOrder.size()-1; i++){
                Character c1 = fightOrder.get(i);
                Character c2 = fightOrder.get(i+1);
                if (c2.getAgility()>c1.getAgility()){
                    fightOrder.set(i, c2);
                    changed = true;
                }
            }
        }
    }

    public void update(CInputProcessor input){
        if (state.equals("fadein")) {
            if (TICK % 9 == 0 && battleStage > 0) {
                battleStage--;
            }
            TICK++;
            if (battleStage == 0 && TICK % 9 == 8) {
                state = "fight";
            }
        } else if (state.equals("fight")){
            if (input.isKeyDown(Input.Keys.ESCAPE)){
                protSide.get(0).setPosition(new Vector2(charPos.x, charPos.y));
                world.kill(enemy);
                Game.GAME_STATE = GameState.WORLD;
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(background, 0, 0, Game.WIDTH(), Game.HEIGHT());

        for (Character c : fightOrder){
            c.render(batch, SCALE);
        }

        if (state.equals("fadein")){
            TextureRegion tr = new TextureRegion(battleTransition, ((battleStage%4)*160), (int)Math.floor((battleStage/4)*90), 160, 90);
            batch.draw(tr, 0, 0, Game.WIDTH(), Game.HEIGHT());
        }
        batch.end();
    }
}
