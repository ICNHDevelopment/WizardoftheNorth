package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.special.Inventory;
import com.icnhdevelopment.wotn.gui.special.SlotType;
import com.icnhdevelopment.wotn.handlers.WizardHelper;
import com.icnhdevelopment.wotn.items.Item;
import com.icnhdevelopment.wotn.world.CollideObject;
import com.icnhdevelopment.wotn.world.InventoryObject;
import com.icnhdevelopment.wotn.world.World;
import com.sun.tools.javac.util.ArrayUtils;

import java.util.*;

/**
 * Created by kyle on 6/4/15.
 */
public class Character extends AnimatedSprite {

    int direction;
    Vector2 tilePosition;
    Rectangle footBox;
    boolean player;
    boolean isTransitioningLayer = false;
    boolean hasRandomMovement = false, isMovingRandomly = true;

    final int SPEED = 2;
    final Random RAN = new Random();

    protected Item[] inventory;
    protected Item[] gear;
    protected Item[] toolbar;

    CollideObject interactObject;

    //Skills
    int level;
    public int getLevel(){ return level; }

    float CurrentExperience;
    public float getCurrentExperience(){ return CurrentExperience; }
    float NextLevelExp;
    float CurrLevelExp;
    public float getRequiredExperience(){
        return NextLevelExp-CurrLevelExp;
    }

    float CurrentVitality;
    public float getCurrentVitality(){ return CurrentVitality; }
    int BaseVitality;
    int IvVitality;
    public int getVitality(){
        double result = level + 10 + ((((BaseVitality+IvVitality)*2)*level)/100);
        return (int)Math.floor(result);
    }

    int BaseAgility;
    int IvAgility;
    public int getAgility(){
        double result = 5 + (((BaseAgility+IvAgility)*2)*level)/100;
        return (int)Math.floor(result);
    }

    int BaseResistance;
    int IvResistance;
    public int getResistance(){
        double result = 5 + (((BaseResistance+IvResistance)*2)*level)/100;
        return (int)Math.floor(result);
    }

    int BaseStrength;
    int IvStrength;
    public int getStrength(){
        double result = 5 + (((BaseStrength+IvStrength)*2)*level)/100;
        return (int)Math.floor(result);
    }

    float CurrentWisdom;
    public float getCurrentWisdom(){ return CurrentWisdom; }
    int BaseWisdom;
    int IvWisdom;
    public int getWisdom(){
        double result = 5 + (((BaseWisdom+IvWisdom)*2)*level)/100;
        return (int)Math.floor(result);
    }
    //EndSkills

    public void create(String filename, int maxFrames, Vector2 position, int animSpeed, boolean player, boolean direcMove){
        super.create(filename, maxFrames, position, new Vector2(), animSpeed);
        inventory = new Item[12];
        gear = new Item[9];
        toolbar = new Item[4];
        regHeight = texture.getHeight()/2;
        width = (int)(regWidth);//*World.SCALE);
        height = (int)(regHeight);//* World.SCALE);
        frame = 0;
        direction = 0;
        footBox = new Rectangle(position.x+width*.2f, position.y, width*.6f, height*.15f);
        this.player = player;
        this.directionalMovement = direcMove;
        if (player){
            level = 1;
            BaseVitality = 35;
            IvVitality = 10;
            CurrentVitality = getVitality();
            BaseAgility = 90;
            IvAgility = 10;
            BaseWisdom = 50;
            IvWisdom = 10;
            CurrentWisdom = getWisdom();
            BaseStrength = 55;
            IvStrength = 10;
            BaseResistance = 30;
            IvResistance = 10;
            CurrentExperience = 0;
            NextLevelExp = CalculateLevelExp(level+1);
            CurrLevelExp = CalculateLevelExp(level);
        }
    }

    float CalculateLevelExp(int currentLevel){
        float nCube = (float)Math.pow((double)currentLevel, 3.0);
        float returnVal;
        if (currentLevel<=15){
            returnVal = ((((currentLevel+1)/3.0f)+24)/50.0f)*nCube;
        }
        else if (currentLevel<=36){
            returnVal = (((currentLevel)+14)/50.0f)*nCube;
        }
        else {
            returnVal = ((((currentLevel)/2.0f)+32)/50.0f)*nCube;
        }
        return (float)Math.floor(returnVal);
    }

    public void move(Vector2 amount, ArrayList<Rectangle> walls, ArrayList<CollideObject> cols){
        animate(true);
        Rectangle next = new Rectangle(position.x + amount.x*SPEED, position.y + amount.y*SPEED, width, height);
        Rectangle nextFoot = new Rectangle(next.x+width*.2f, next.y, width*.6f, height*.15f);
        if (canMove(nextFoot, walls, cols)) {
            footBox = nextFoot;
            position.x += amount.x * SPEED;
            position.y += amount.y * SPEED;
            if (directionalMovement) {
                if (amount.y == 0) {
                    if (amount.x > 0) {
                        direction = 1;
                    } else {
                        direction = 0;
                    }
                }
            }
        }
    }

    boolean canMove(Rectangle r, ArrayList<Rectangle> walls, ArrayList<CollideObject> cols){
        for (Rectangle a : walls){
            if (a.overlaps(r)){
                return false;
            }
        }
        for (CollideObject c : cols){
            if (c.isVisible() && r.overlaps(c.getHitbox())){
                return false;
            }
        }
        return true;
    }

    public void updateWalls(TiledMap map, ArrayList<Rectangle> overs){
        if (!isTransitioningLayer) {
            boolean changedLayer = false;
            for (Rectangle o : overs) {
                if (footBox.overlaps(o)) {
                    changedLayer = true;
                    if ((map.getLayers().get("overwalls").getOpacity()>0))
                        transitionLayer(map.getLayers().get("overwalls"), -.005f);
                    else
                        map.getLayers().get("overwalls").setOpacity(0f);
                }
            }
            if (!changedLayer){
                changedLayer = true;
                if ((map.getLayers().get("overwalls").getOpacity()<1))
                    transitionLayer(map.getLayers().get("overwalls"), .005f);
                else
                    map.getLayers().get("overwalls").setOpacity(1f);
            }
        }
    }

    public void updateInteractObjects(ArrayList<CollideObject> cos){
        CollideObject closest = null;
        float lowestDistance = Float.MAX_VALUE;
        for (CollideObject c : cos) {
            float dis = WizardHelper.getDistanceFromCenter(getHitBox(), c.getHitbox());
            if (dis < lowestDistance) {
                if (closest!=null){
                    c.setInteractable(false);
                }
                lowestDistance = dis;
                closest = c;
            }
        }
        interactObject = closest;
        if (interactObject!=null){
            if (lowestDistance<32) {
                interactObject.setInteractable(true);
            } else {
                interactObject.setInteractable(false);
            }
        }
    }

    void transitionLayer(MapLayer l, float step){
        final MapLayer m = l;
        final float s = step;
        new Timer().schedule(new TimerTask(){
            @Override
            public void run() {
                if ((s<0&&m.getOpacity()>0)||(m.getOpacity()<1&&s>0)){
                    isTransitioningLayer = true;
                    m.setOpacity(m.getOpacity()+s);
                }else{
                    isTransitioningLayer = false;
                    this.cancel();
                }
            }
        }, new Date(), 1);
    }

    public void setRandomMovementTimer(ArrayList<Rectangle> w, ArrayList<CollideObject> cols){
        final ArrayList<Rectangle> walls = w;

        final Thread movementThread = new Thread(){
            @Override
            public  void run(){
                boolean running = true;
                while (running) {
                    long delay = (long) (1000 * (2 + RAN.nextDouble() * 2));
                    try {
                        Thread.sleep(delay);
                        moveRandomly(walls, cols);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        running = false;
                    }
                }
            }
        };

        movementThread.start();
    }

    void moveRandomly(ArrayList<Rectangle> w, ArrayList<CollideObject> cols){
        if (isMovingRandomly){
            Random r = new Random();
            ArrayList<Vector2> direcs = getMoveDirections(w, cols);
            if (direcs.size()>0) {
                Vector2 avail = direcs.get(r.nextInt(direcs.size()));
                int xDir = (int)avail.x, yDir = (int)avail.y;
                animating = true;
                while (animating) {
                    move(new Vector2(xDir, yDir), w, cols);
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                frame = 0;
            }
        }else {
            //Pathfind to a target
        }
    }

    ArrayList<Vector2> getMoveDirections(ArrayList<Rectangle> w, ArrayList<CollideObject> cols){
        ArrayList<Vector2> dirs = new ArrayList<>();
        int DistanceNeeded = SPEED*maxFrames;
        for (int i = -1; i<=1; i++){
            for (int j = -1; j<=1; j++){
                Rectangle next = new Rectangle(position.x + i*DistanceNeeded, position.y + j*DistanceNeeded, width, height);
                Rectangle nextFoot = new Rectangle(next.x+width*.2f, next.y, width*.6f, height*.15f);
                if (canMove(nextFoot, w, cols)&&!(i==0&&j==0)){
                    int retI = i, retJ = j;
                    if (i!=0&&j!=0){
                        if (RAN.nextBoolean()){
                            retI = 0;
                        } else {
                            retJ = 0;
                        }
                    }
                    dirs.add(new Vector2(retI, retJ));
                }
            }
        }
        return dirs;
    }

    public void animate(boolean moving) {
        if (World.TICK % speed == 0) {
            if (moving) {
                this.animate();
            } else if (frame > 0) {
                if (frame < maxFrames / 2) {
                    frame--;
                } else {
                    frame++;
                    if (frame >= maxFrames) {
                        animating = false;
                        frame = 0;
                    }
                }
            }
        }
    }

    public void render(SpriteBatch batch){
        TextureRegion tr = TextureRegion.split(texture, (int)regWidth, (int)regHeight)[direction][frame];
        batch.draw(tr, position.x, position.y, width, height);
    }

    public void interact(){
        if (interactObject!=null) {
            if (interactObject instanceof InventoryObject) {
                InventoryObject invenObject = (InventoryObject) interactObject;
                if (!invenObject.isOpened()) {
                    addToInventory(new Item(Item.ITEMS.get("DaggerStone")));
                    invenObject.setOpened(true);
                }
            } else {
                if (interactObject.isBreakable()){
                    SlotType bi = interactObject.getBreakItem();
                    if (bi.equals(SlotType.NORM)||(bi.equals(SlotType.WEAPON)&&gear[8]!=null)){
                        interactObject.setVisible(false);
                    }
                }
            }
        }
    }

    protected void addToInventory(Item item){
        for (int i = 0; i<inventory.length; i++){
            if (inventory[i] == null){
                inventory[i] = item;
                return;
            }
        }
    }

    public void swapItemFromInventory(Item mouse, int itemSlot){
        inventory[itemSlot] = mouse;
    }

    public void swapItemFromGear(Item mouse, int itemSlot){
        gear[itemSlot-12] = mouse;
    }

    public void swapItemFromToolbar(Item mouse, int itemSlot){
        toolbar[itemSlot-21] = mouse;
    }

    public boolean isPlayer() { return player; }

    public Rectangle getHitBox() { return new Rectangle(footBox.x, footBox.y, footBox.width, footBox.height); }

    public Item[] getInventory() {
        return WizardHelper.concat(WizardHelper.concat(inventory, gear), toolbar);
    }

    public Item[] getToolbar(){
        return toolbar;
    }
}
