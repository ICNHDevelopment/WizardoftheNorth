package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.battle.battlegui.BattleMenu;
import com.icnhdevelopment.wotn.battle.battlegui.BattleMenuMain;
import com.icnhdevelopment.wotn.gui.Fonts;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.GameState;
import com.icnhdevelopment.wotn.players.Character;
import com.icnhdevelopment.wotn.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by kyle on 1/23/16.
 */
public class Battle {

    Texture battleTransition;
    Texture background;
    String state = "fadein";
    String stateState = "chooseaction";
    int battleStage = 7;
    Vector2 charPos;
    Character enemy;
    World world;
    BitmapFont font;
    Texture vitBar, wisBar;
    Random randomGenerator;
    boolean showOptions = false;
    Texture orderBack;
    Texture orderOver;
    Rectangle orderContainerRec;
    float orderWidth = 70, orderSpace = 5;

    int TICK = 1;
    int SCALE = 3;

    ArrayList<Rectangle> protPositions;
    Rectangle protDataPos;
    ArrayList<Rectangle> antPositions;
    Rectangle antDataPos;
    ArrayList<Character> protSide;
    ArrayList<Character> antSide;
    ArrayList<Character> fightOrder;
    ArrayList<CharacterData> characterData;
    BattleMenuMain bm;
    Character charTurn;
    int whoseturn = 0;
    ActionDoer actionDoer;

    public void create(BattleInfo battleInfo){
        font = Fonts.loadFont(Fonts.OPEN_SANS, 12, Color.WHITE, Color.BLACK);
        vitBar = new Texture("ui/hud/VitalityMeter.png");
        wisBar = new Texture("ui/hud/WisdomMeter.png");
        orderBack = new Texture("ui/battle/orderUnderlay.png");
        orderOver = new Texture("ui/battle/orderOverlay.png");
        randomGenerator = new Random();
        background = new Texture(battleInfo.getBackFile());
        battleTransition = battleInfo.battleTex;
        protSide = battleInfo.getProtSide();
        antSide = battleInfo.getAntSide();
        charPos = battleInfo.getCharacterWorldPosition();
        enemy = battleInfo.getEnemy();
        world = battleInfo.getWorld();
        loadPositions();
        setPositions(protSide, protPositions);
        setPositions(antSide, antPositions);
        setBattleOrder();
        characterData = new ArrayList<>();
        setData(protSide, protDataPos, true);
        setData(antSide, antDataPos, false);
        bm = new BattleMenuMain();
        actionDoer = new ActionDoer();

        orderContainerRec = new Rectangle((Game.WIDTH()-(orderWidth*fightOrder.size()+orderSpace*(fightOrder.size()-1)))/2, Game.HEIGHT()-80, (70*fightOrder.size()+5*(fightOrder.size()-1)), 70);
    }

    void loadPositions(){
        protPositions = new ArrayList<>();
        protPositions.add(new Rectangle(387, 351, 90, 90));
        protPositions.add(new Rectangle(297, 447, 90, 90));
        protPositions.add(new Rectangle(297, 255, 90, 90));
        protPositions.add(new Rectangle(195, 351, 90, 90));
        protPositions.add(new Rectangle(105, 447, 90, 90));
        protPositions.add(new Rectangle(105, 255, 90, 90));
        protDataPos = new Rectangle(6, 6, 351, 228);
        antPositions = new ArrayList<>();
        antPositions.add(new Rectangle(867, 351, 90, 90));
        antPositions.add(new Rectangle(957, 447, 90, 90));
        antPositions.add(new Rectangle(957, 255, 90, 90));
        antPositions.add(new Rectangle(1059, 351, 90, 90));
        antPositions.add(new Rectangle(1149, 447, 90, 90));
        antPositions.add(new Rectangle(1149, 255, 90, 90));
        antDataPos = new Rectangle(924, 6, 351, 228);
    }

    void setPositions(ArrayList<Character> side, ArrayList<Rectangle> recs){
        for (int i = 0; i<side.size(); i++){
            Character c = side.get(i);
            Rectangle r = recs.get(i);
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
                    CharacterData cd2 = characterData.get(i+1);
                    characterData.set(i, cd2);
                    changed = true;
                }
            }
        }
    }

    void setData(ArrayList<Character> side, Rectangle dataRec, boolean s){
        float top = dataRec.y + dataRec.height;
        float left = dataRec.x;
        float width = dataRec.width;
        float height = dataRec.height/6;
        for (int i = 0; i<side.size(); i++){
            Character c = side.get(i);
            Rectangle r = new Rectangle(left, top-(i*height), width, height);
            CharacterData temp = new CharacterData(c, r, s);
            characterData.add(temp);
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
                charTurn = fightOrder.get(whoseturn);
            }
        } else if (state.equals("fight")){
            if (protSide.contains(charTurn)){
                if (stateState.equals("chooseaction")) {
                    showOptions = true;
                    bm.update(input, this);
                    if (BattleMenuMain.choseAction) {
                        stateState = "doaction";
                    }
                } else if (stateState.equals("doaction")){
                    showOptions = false;
                    if (actionDoer.doAction(this)){
                        whoseturn++;
                        if (whoseturn>fightOrder.size()){
                            whoseturn = 0;
                        }
                        charTurn = fightOrder.get(whoseturn);
                    }
                }
            }else{
                showOptions = false;
                //DO AI STUFF
            }
            if (input.isKeyDown(Input.Keys.ESCAPE)){
                protSide.get(0).setPosition(new Vector2(charPos.x, charPos.y));
                world.kill(enemy);
                Game.GAME_STATE = GameState.WORLD;
            }
            for (CharacterData cd : characterData){
                cd.updateData();
            }
        }
    }

    public void render(SpriteBatch batch){
        batch.begin();
        batch.draw(background, 0, 0, Game.WIDTH(), Game.HEIGHT());
        for (int i = 0; i<fightOrder.size(); i++){
            Character c = fightOrder.get(i);
            c.render(batch, SCALE);
            Rectangle orderSpot = new Rectangle(orderContainerRec.x + orderWidth*i+orderSpace*i, orderContainerRec.y, orderWidth, orderWidth);
            batch.draw(orderBack, orderSpot.x, orderSpot.y, orderSpot.width, orderSpot.height);
            batch.draw(c.getImage(), orderSpot.x, orderSpot.y, orderSpot.width, orderSpot.height);
            if (i==whoseturn) {
                batch.draw(orderOver, orderSpot.x, orderSpot.y, orderSpot.width, orderSpot.height);
            }
        }
        for (CharacterData cd : characterData){
            Character c = cd.getCharacter();
            String name = cd.getName();
            Vector2 np = cd.getNamePos();
            font.draw(batch, name, np.x, np.y);
            Rectangle temp = cd.getVitBarRec();
            float tempwidth = temp.width*cd.getVitPerc();
            batch.draw(vitBar, temp.x, temp.y, tempwidth, temp.height);
            if (cd.showNumbers) {
                font.draw(batch, (int) c.getCurrentVitality() + "/" + c.getVitality(), temp.x + temp.width + 2, temp.y + temp.height);
            }
            temp = cd.getWisBarRec();
            tempwidth = temp.width*cd.getWisPerc();
            batch.draw(wisBar, temp.x, temp.y, tempwidth, temp.height);
            if (cd.showNumbers) {
                font.draw(batch, (int) c.getCurrentWisdom() + "/" + c.getWisdom(), temp.x + temp.width + 2, temp.y + temp.height);
            }
        }
        if (showOptions) {
            bm.render(batch);
        }
        if (state.equals("fadein")){
            TextureRegion tr = new TextureRegion(battleTransition, ((battleStage%4)*160), (int)Math.floor((battleStage/4)*90), 160, 90);
            batch.draw(tr, 0, 0, Game.WIDTH(), Game.HEIGHT());
        }
        batch.end();
    }

    public ArrayList<Character> getAntagonists(){
        return antSide;
    }

    public ArrayList<Character> getFightOrder(){
        return fightOrder;
    }

    public ArrayList<CharacterData> getCharacterData(){
        return characterData;
    }

    public Character currentTurn(){
        return charTurn;
    }

    public void setAction(Object o, boolean gg, Character d, Character r){
        actionDoer.setAction(o, gg);
        actionDoer.setCharacters(d, r);
    }
}
