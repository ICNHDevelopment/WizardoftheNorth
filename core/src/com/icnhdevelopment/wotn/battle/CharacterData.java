package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.players.Character;

/**
 * Created by kyle on 1/24/16.
 */
public class CharacterData {

    private Character character;
    Rectangle position;
    private String name;
    private Vector2 namePos;
    private Rectangle vitBarRec;
    private Rectangle wisBarRec;
    private float vitPerc;
    private float wisPerc;
    boolean showNumbers;

    public CharacterData(Character c, Rectangle pos, boolean sn){
        this.character = c;
        this.position = pos;
        this.showNumbers = sn;
        name = c.getName();
        updateData();

        setPositions();
    }

    void setPositions(){
        float x = position.x + 2;
        float y = position.y - 2;
        namePos = new Vector2(x, y);

        y = position.y - position.height/2 - 6;
        vitBarRec = new Rectangle(x, y, position.width/4*3, 12);
        y -= 12;
        wisBarRec = new Rectangle(x, y, position.width/4*3, 12);
    }

    public void updateData(){
        vitPerc = getCharacter().getCurrentVitality()/ getCharacter().getVitality();
        wisPerc = getCharacter().getCurrentWisdom()/ getCharacter().getWisdom();
    }

    public Vector2 getNamePos() {
        return namePos;
    }

    public Rectangle getVitBarRec() {
        return vitBarRec;
    }

    public Rectangle getWisBarRec() {
        return wisBarRec;
    }

    public float getVitPerc() {
        return vitPerc;
    }

    public float getWisPerc() {
        return wisPerc;
    }

    public String getName() {
        return name;
    }

    public Character getCharacter() {
        return character;
    }
}
