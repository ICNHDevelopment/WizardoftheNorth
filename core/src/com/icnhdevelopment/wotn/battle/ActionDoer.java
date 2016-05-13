package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.icnhdevelopment.wotn.players.Character;

/**
 * Created by kyle on 5/12/16.
 */
public class ActionDoer {

    Object action = null;
    Character doer, receiver;
    float actionDuration = 0; //IN SECONDS ie. 0.5 = 500ms

    public void setAction(Object o){
        action = o;
        actionDuration = 0;
    }

    public void setCharacters(Character d, Character r){
        doer = d;
        receiver = r;
    }

    public boolean doAction(){
        if (action instanceof String){
            String act = (String)action;
            if (act.equals("protect")){
                if (actionDuration>3){
                    doer.setDrawTint(new Color(Color.WHITE));
                    return true;
                } else if (actionDuration>2.5){
                    doer.setDrawTint(new Color(Color.BLUE));
                } else if (actionDuration>2){
                    doer.setDrawTint(new Color(Color.WHITE));
                } else if (actionDuration>1.5){
                    doer.setDrawTint(new Color(Color.BLUE));
                } else if (actionDuration>1){
                    doer.setDrawTint(new Color(Color.WHITE));
                } else if (actionDuration>0.5){
                    doer.setDrawTint(new Color(Color.BLUE));
                }
            }
        }
        actionDuration += Gdx.graphics.getDeltaTime();
        return false;
    }

}
