package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.players.Character;
import com.icnhdevelopment.wotn.players.CharacterStats;
import com.icnhdevelopment.wotn.players.Projectile;

/**
 * Created by kyle on 5/12/16.
 */
public class ActionDoer {

    Object action = null;
    String actionType = "";
    final String ATTACK = "slash.range";
    final String SUPPORT = "protect.focus";
    Character doer, receiver;
    boolean goodGuy;
    float actionDuration = 0; //IN SECONDS ie. 0.5 = 500ms

    Projectile projectile;

    public void setAction(Object o, boolean gg){
        action = o;
        setActionType(o);
        goodGuy = gg;
        actionDuration = 0;
        if (o instanceof String){
            if (o.equals("range")){
                projectile = new Projectile();
            }
        }
    }

    void setActionType(Object action){
        if (action instanceof String){
            String strAct = (String)action;
            if (ATTACK.contains(strAct)){
                actionType = "Attack";
            } else if (SUPPORT.contains(strAct)){
                actionType = "Support";
            }
        }
    }

    public void setCharacters(Character d, Character r){
        doer = d;
        receiver = r;
    }

    public boolean doAction(Battle battle){
        if (action instanceof String){
            if (action.equals("protect")){
                if (flashColor(doer, Color.BLUE, 2, 1)){
                    CharacterStats stats = doer.getCharacterStats();
                    stats.addModifiers(new int[] {0, 0, 1, 0, 0});
                    return true;
                }
            } else if (action.equals("focus")){
                if (flashColor(doer, Color.MAGENTA, 2, 1)){
                    for (Character a : battle.getAntagonists()){
                        a.getCharacterStats().addModifiers(new int[] {0, -1, 0, 0, 0});
                    }
                    return true;
                }
            } else if (action.equals("slash")){
                if (attackActorWithActor(doer, receiver, 0.8f)){
                    receiver.damage(doer.getDamage(doer, receiver));
                    doer.setDrawOffset(new Vector2(0, 0));
                    return true;
                }
            } else if (action.equals("range")){
                if (doRangedAttack(doer, receiver, 2f)){
                    receiver.damage(doer.getDamage(doer, receiver));
                    doer.setDrawOffset(new Vector2(0, 0));
                    return true;
                }
            }
        }
        actionDuration += Gdx.graphics.getDeltaTime();
        return false;
    }

    boolean doRangedAttack(Character mover, Character getHit, float time){
        int direction = goodGuy?1:-1;
        float oneEighthTime = time/5f;
        if (actionDuration<oneEighthTime){
            mover.setDrawOffset(new Vector2(direction*250, (getHit.getPosition().y-mover.getPosition().y)));
            mover.setFrame(0);
        } else if (actionDuration < oneEighthTime*5){
            mover.animateAttack(oneEighthTime*4, actionDuration-oneEighthTime);
        } else if (actionDuration < oneEighthTime*8){
            mover.animateAttack(oneEighthTime*4, actionDuration-oneEighthTime);
            mover.setDrawOffset(new Vector2(0, 0));
            mover.setFrame(0);
            if (projectile.getPosition() == null){
                projectile.create(mover.getAttackAnimation(), new Vector2(mover.getPosition().x + direction*250, mover.getPosition().y+(getHit.getPosition().y-mover.getPosition().y)), mover.getSize());
            }
            projectile.setPosition(new Vector2(projectile.getPosition().x+5*direction, projectile.getPosition().y));
            getHit.setDrawTint(new Color(Color.RED));
        } else {
            projectile = null;
            getHit.setDrawTint(new Color(Color.WHITE));
            return true;
        }
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

    public String getActionDescription(){
        return doer.getName() + " chose to use a " + action + " " + actionType.toLowerCase() + " move...";
    }

    public void render(SpriteBatch batch){
        if (projectile != null && projectile.getPosition()!=null){
            projectile.render(batch);
        }
    }
}
