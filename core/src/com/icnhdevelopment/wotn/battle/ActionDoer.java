package com.icnhdevelopment.wotn.battle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    final String ITEM = "consume";
    Character doer, receiver;
    boolean goodGuy;
    float actionDuration = 0; //IN SECONDS ie. 0.5 = 500ms
    boolean attackMiss = false;

    Projectile projectile;

    public void setAction(Battle battle, Object o, boolean gg){
        action = o;
        setActionType(o);
        goodGuy = gg;
        actionDuration = 0;
        if (o instanceof String){
            if (o.equals("range")){
                projectile = new Projectile();
            }
        }
        if (actionType.equals("Attack")){
            int index = battle.getFightOrder().indexOf(doer);
            CharacterData dat = battle.getCharacterData().get(index);
            doer.setDrawOffset(new Vector2(0, 0));
            attackMiss = !dat.willHit();
            if (doer.getCurrentVitality()<=0){
                attackMiss = false;
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
            } else if (ITEM.contains(strAct)){
                actionType = "Item";
            }
        }
    }

    public void setCharacters(Character d, Character r){
        doer = d;
        receiver = r;
    }

    public String doAction(Battle battle){
        if (action instanceof String){
            if (action.equals("protect")){
                if (flashColor(doer, Color.BLUE, 2, 1)){
                    CharacterStats stats = doer.getCharacterStats();
                    stats.addModifiers(new int[] {0, 0, 1, 0, 0});
                    return "true";
                }
            } else if (action.equals("focus")){
                if (flashColor(doer, Color.MAGENTA, 2, 1)){
                    int index = battle.getFightOrder().indexOf(doer);
                    CharacterData dat = battle.getCharacterData().get(index);
                    dat.focus();
                    return "true";
                }
            } else if (action.equals("slash")){
                if (attackActorWithActor(doer, receiver, 2f)){
                    if (!attackMiss){
                        receiver.damage(doer.getDamage(doer, receiver));
                        return "true";
                    } else {
                        return "miss";
                    }
                }
            } else if (action.equals("range")){
                if (doRangedAttack(doer, receiver, 2f)){
                    if (!attackMiss){
                        receiver.damage(doer.getDamage(doer, receiver));
                        return "true";
                    } else {
                        return "miss";
                    }
                }
            } else if (action.equals("consume")){
                if (doConsume(doer, 2f)){
                    return "true";
                }
            }
        }
        actionDuration += Gdx.graphics.getDeltaTime();
        return "false";
    }

    boolean doConsume(Character character, float time){
        if (actionDuration<time/3f){
            character.animateConsume();
            character.setFrame(0);
        } else if (actionDuration<time/3f*2f){
            character.animateConsume();
            character.setFrame(1);
        } else if (actionDuration<time) {
            character.animateConsume();
            character.setFrame(0);
        } else {
            character.animateIdle();
            return true;
        }
        return false;
    }

    boolean doRangedAttack(Character mover, Character getHit, float time){
        int direction = goodGuy?1:-1;
        float oneEighthTime = time/5f;
        if (actionDuration<oneEighthTime){
            mover.setDrawOffset(new Vector2(direction*250, (getHit.getPosition().y-mover.getPosition().y)));
            mover.setFrame(0);
        } else if (actionDuration < oneEighthTime*5){
            mover.animateRanged(oneEighthTime*4, actionDuration-oneEighthTime);
        } else if (actionDuration < oneEighthTime*8){
            mover.animateRanged(oneEighthTime*4, actionDuration-oneEighthTime);
            mover.setDrawOffset(new Vector2(0, 0));
            mover.setFrame(0);
            if (projectile.getPosition() == null){
                projectile.create(mover.getRangeAnimation(), new Vector2(mover.getPosition().x + direction*250, mover.getPosition().y+(getHit.getPosition().y-mover.getPosition().y)), mover.getSize());
            }
            projectile.setPosition(new Vector2(projectile.getPosition().x+5*direction, projectile.getPosition().y));
            if (!attackMiss) {
                flashColor(getHit, new Color(Color.RED), time, 2);
            }
        } else {
            projectile = null;
            getHit.setDrawTint(new Color(Color.WHITE));
            return true;
        }
        return false;
    }

    boolean attackActorWithActor(Character mover, Character getHit, float time){
        int direction = goodGuy?1:-1;
        mover.animateAttack();
        if (actionDuration<time/8f){
            float diffY = getHit.getPosition().y - mover.getPosition().y;
            float diffX = getHit.getPosition().x - mover.getPosition().x - (getHit.getSize().x*2)*direction;
            mover.setFrame(2);
            mover.setDrawOffset(new Vector2(diffX, diffY));
        } else if (actionDuration<time*(3f/4f)){
            mover.setFrame(3);
            if (!attackMiss) {
                getHit.setDrawTint(new Color(Color.RED));
            }
        } else if (actionDuration<time){
            mover.animateIdle();
            getHit.setDrawTint(new Color(Color.WHITE));
            mover.setDrawOffset(Vector2.Zero);
        } else {
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
        if (actionType.equals("Item")){
            return doer.getName() + " chose to " + action + " an " + actionType.toLowerCase() + "...";
        } else {
            return doer.getName() + " chose to use a " + action + " " + actionType.toLowerCase() + " move...";
        }
    }

    public void render(SpriteBatch batch){
        if (projectile != null && projectile.getPosition()!=null){
            projectile.render(batch);
        }
    }
}
