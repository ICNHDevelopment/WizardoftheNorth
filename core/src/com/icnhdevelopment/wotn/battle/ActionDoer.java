package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.players.Character;
import com.icnhdevelopment.wotn.players.CharacterStats;

/**
 * Created by kyle on 5/12/16.
 */
public class ActionDoer {

    Object action = null;
    Character doer, receiver;
    boolean goodGuy;
    float actionDuration = 0; //IN SECONDS ie. 0.5 = 500ms

    public void setAction(Object o, boolean gg){
        action = o;
        goodGuy = gg;
        actionDuration = 0;
    }

    public void setCharacters(Character d, Character r){
        doer = d;
        receiver = r;
    }

    public boolean doAction(Battle battle){
        if (action instanceof String){
            String act = (String)action;
            if (act.equals("protect")){
                if (flashColor(doer, Color.BLUE, 3, 3)){
                    CharacterStats stats = doer.getCharacterStats();
                    stats.addModifiers(new int[] {0, 0, 1, 0, 0});
                    return true;
                }
            } else if (act.equals("focus")){
                if (flashColor(doer, Color.MAGENTA, 3, 3)){
                    for (Character a : battle.getAntagonists()){
                        a.getCharacterStats().addModifiers(new int[] {0, -1, 0, 0, 0});
                    }
                    return true;
                }
            } else if (act.equals("slash")){
                if (attackActorWithActor(doer, receiver, 0.8f)){
                    receiver.damage(doer.getDamage(doer, receiver));
                    doer.setDrawOffset(new Vector2(0, 0));
                    return true;
                }
            }
        }
        actionDuration += Gdx.graphics.getDeltaTime();
        return false;
    }

    boolean attackActorWithActor(Character mover, Character getHit, float time){
        int direction = goodGuy?1:-1;
        float moveDisplacement = 250;
        float deltaMove = (moveDisplacement/time)*Gdx.graphics.getDeltaTime()*direction;
        if (actionDuration<time/2){
            mover.changeDrawOffset(new Vector2(deltaMove, 0));
        } else if (actionDuration<time){
            mover.changeDrawOffset(new Vector2(-deltaMove, 0));
            flashColor(getHit, new Color(Color.RED), time, 2);
        } else {
            doer.setDrawTint(new Color(Color.WHITE));
            flashColor(getHit, new Color(Color.RED), time, 2);
            return true;
        }
        return false;
    }

    boolean flashColor(Character flasher, Color color, float maxTime, int flashes){
        if (actionDuration>maxTime) {
            flasher.setDrawTint(new Color(Color.WHITE));
            return true;
        }
        float changeFreq = maxTime/((float)flashes*2.0f);
        for (int i = 1; i<flashes*2; i++){
            if (actionDuration >= changeFreq*i){
                if (i%2==1){
                    flasher.setDrawTint(new Color(color));
                } else {
                    flasher.setDrawTint(new Color(Color.WHITE));
                }
            }
        }
        return false;
    }
}
