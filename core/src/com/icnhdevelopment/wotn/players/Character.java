package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.special.SlotType;
import com.icnhdevelopment.wotn.handlers.WizardHelper;
import com.icnhdevelopment.wotn.items.BattleItem;
import com.icnhdevelopment.wotn.items.Item;
import com.icnhdevelopment.wotn.items.SpecialItem;
import com.icnhdevelopment.wotn.world.CollideObject;
import com.icnhdevelopment.wotn.world.InventoryObject;
import com.icnhdevelopment.wotn.world.World;

import java.util.*;

/**
 * Created by kyle on 6/4/15.
 */
public class Character extends AnimatedSprite {

    int direction;
    Rectangle footBox;
    boolean player;
    boolean isTransitioningLayer = false;
    boolean isMovingRandomly = true;
    protected String name;

    final int SPEED = 2;
    final Random RAN = new Random();

    protected Item[] inventory;
    protected Item[] gear;
    protected Item[] toolbar;

    CollideObject interactObject;
    NPCharacter interactCharacter;

    ArrayList<Character> followers;
    ArrayList<Vector2> lastPositions;

    CharacterStats stats;
    public int getLevel(){ return stats.getLevel(); }
    public float getCurrentExperience(){ return stats.getCurrentExperience(); }
    public float getRequiredExperience(){
        return stats.getRequiredExperience();
    }
    public void addExperience(int exp) { stats.addExperience(exp); }
    public float getCurrentVitality(){ return stats.getCurrentVitality(); }
    public int getVitality(){ return stats.getVitality(); }
    public float getBonusVitality(){
        if (inventory!=null) {
            float bonus = 0;
            for (int i = 12; i < 21; i++) {
                SpecialItem si = (SpecialItem) getInventory()[i];
                if (si != null) {
                    bonus += si.VitalityBonus;
                }
            }
            return bonus;
        }
        return 0;
    }
    public int getAgility(){ return stats.getAgility(); }
    public float getBonusAgility(){
        if (inventory!=null) {
            float bonus = 0;
            for (int i = 12; i < 21; i++) {
                SpecialItem si = (SpecialItem) getInventory()[i];
                if (si != null) {
                    bonus += si.AgilityBonus;
                }
            }
            return bonus;
        }
        return 0;
    }
    public int getResistance(){ return stats.getResistance(); }
    public float getBonusResistance(){
        if (inventory!=null) {
            float bonus = 0;
            for (int i = 12; i < 21; i++) {
                SpecialItem si = (SpecialItem) getInventory()[i];
                if (si != null) {
                    bonus += si.ResistanceBonus;
                }
            }
            return bonus;
        }
        return 0;
    }
    public int getStrength(){ return stats.getStrength(); }
    public float getBonusStrength(){
        if (inventory!=null) {
            float bonus = 0;
            for (int i = 12; i < 21; i++) {
                SpecialItem si = (SpecialItem) getInventory()[i];
                if (si != null) {
                    bonus += si.StrengthBonus;
                }
            }
            return bonus;
        }
        return 0;
    }
    public float getCurrentWisdom(){ return stats.getCurrentWisdom(); }
    public int getWisdom(){ return stats.getWisdom(); }
    public float getBonusWisdom(){
        if (inventory!=null) {
            float bonus = 0;
            for (int i = 12; i < 21; i++) {
                SpecialItem si = (SpecialItem) getInventory()[i];
                if (si != null) {
                    bonus += si.WisdomBonus;
                }
            }
            return bonus;
        }
        return 0;
    }
    public CharacterStats getCharacterStats() { return stats; }
    public float getDamage(Character a, Character b) { return stats.CalculateDamage(a, b); }
    public boolean damage(float damage) { return stats.damage(damage); }
    public void heal(float heal) { stats.heal(heal); }
    public void remember(float rem) { stats.remember(rem); }

    public void create(String filelocation, String prefix, int maxFrames, Vector2 position, int animSpeed, boolean player, boolean direcMove){
        super.create(filelocation, prefix, maxFrames, position, new Vector2(), animSpeed);
        inventory = new Item[12];
        gear = new Item[9];
        toolbar = new Item[4];
        regHeight = texture.getHeight()/2;
        width = (int)(regWidth);//*World.SCALE);
        height = (int)(regHeight);//* World.SCALE);
        frame = 0;
        direction = 1;
        footBox = new Rectangle(position.x+width*.2f, position.y, width*.6f, height*.15f);
        this.player = player;
        this.directionalMovement = direcMove;
        followers = new ArrayList<>();
        lastPositions = new ArrayList<>();
        if (player){
            stats = new CharacterStats(this, 1, 35, 90, 30, 55, 50);
            name = "You";
        }
    }

    public void create(String filelocation, String prefix, int maxFrames, Vector2 position, int animSpeed, boolean direcMove, CharacterStats stats) {
        super.create(filelocation, prefix, maxFrames, position, new Vector2(), animSpeed);
        regHeight = texture.getHeight()/2;
        width = (int)(regWidth);//*World.SCALE);
        height = (int)(regHeight);//* World.SCALE);
        frame = 0;
        direction = 0;
        footBox = new Rectangle(position.x+width*.2f, position.y, width*.6f, height*.15f);
        this.stats = stats;
        name = this.getClass().getSimpleName();
    }

    public void moveByHitBoxToPosition(Vector2 position){
        this.getPosition().y = position.y - height*.15f;
        this.getPosition().x = position.x - width*.15f/2;
        Rectangle nextFoot = new Rectangle(this.getPosition().x+width*.15f, this.getPosition().y, width*.7f, height*.15f);
    }

    public void move(Vector2 amount, ArrayList<Rectangle> walls, ArrayList<CollideObject> cols, ArrayList<NPCharacter> npcs){
        animate(true);
        Rectangle next = new Rectangle(getPosition().x + amount.x*SPEED, getPosition().y + amount.y*SPEED, width, height);
        Rectangle nextFoot = new Rectangle(next.x+width*.15f, next.y, width*.7f, height*.15f);
        if (canMove(nextFoot, walls, cols, npcs)) {
            lastPositions.add(0, new Vector2(getPosition()));
            while (lastPositions.size()>(followers.size()+1)*16) {
                lastPositions.remove(lastPositions.size()-1);
            }
            footBox = nextFoot;
            getPosition().x += amount.x * SPEED;
            getPosition().y += amount.y * SPEED;
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

    boolean canMove(Rectangle r, ArrayList<Rectangle> walls, ArrayList<CollideObject> cols, ArrayList<NPCharacter> npcs){
        for (Rectangle a : walls){
            if (a.overlaps(r)){
                return false;
            }
        }
        for (CollideObject c : cols){
            if (c.isVisible() && r.overlaps(c.getHitBox())){
                return false;
            }
        }
        for (NPCharacter n : npcs){
            if (!(followers.contains(n)) && r.overlaps(n.getHitBox())){
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
            boolean canInteract = true;
            if (!c.isVisible()){
                canInteract = false;
            } else{
                if (c instanceof InventoryObject){
                    if (((InventoryObject)c).isOpened()){
                        canInteract = false;
                    }
                }
            }
            if (canInteract) {
                float dis = WizardHelper.getDistanceFromCenter(getHitBox(), c.getHitBox());
                if (dis < lowestDistance) {
                    if (closest != null) {
                        c.setInteractable(false);
                    }
                    lowestDistance = dis;
                    closest = c;
                }
            }
        }
        interactObject = closest;
        if (interactObject!=null){
            if (lowestDistance<48) {
                interactObject.setInteractable(true);
            } else {
                interactObject.setInteractable(false);
                interactObject = null;
            }
        }
    }

    public void updateNPCS(ArrayList<NPCharacter> npcs){
        NPCharacter closest = null;
        float lowestDistance = Float.MAX_VALUE;
        for (NPCharacter c : npcs) {
            if (!followers.contains(c)) {
                float dis = WizardHelper.getDistanceFromCenter(getHitBox(), c.getHitBox());
                if (dis < lowestDistance) {
                    if (closest != null) {
                        c.setInteractable(false);
                    }
                    lowestDistance = dis;
                    closest = c;
                }
            }
        }
        interactCharacter = closest;
        if (interactObject==null&&interactCharacter!=null){
            if (lowestDistance<24) {
                interactCharacter.setInteractable(true);
            } else {
                interactCharacter.setInteractable(false);
            }
        }
    }

    public void updateFollowers(boolean moving){
        for (int i = 0; i<followers.size(); i++){
            if ((i+1)*16-1<lastPositions.size()) {
                Vector2 newPos = new Vector2(lastPositions.get((i+1)*16-1));
                Character f = followers.get(i);
                if (f.getPosition().x<newPos.x){
                    f.setDirection(1);
                } else if (f.getPosition().x>newPos.x) {
                    f.setDirection(0);
                }
                f.setPosition(newPos);
                f.animate(moving);
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

    public void setRandomMovementTimer(ArrayList<Rectangle> w, ArrayList<CollideObject> cols, World world, ArrayList<NPCharacter> npcs){
        final ArrayList<Rectangle> walls = w;

        final Thread movementThread = new Thread(){
            @Override
            public  void run(){
                boolean running = true;
                while (running) {
                    long delay = (long) (1000 * (2 + RAN.nextDouble() * 2));
                    try {
                        Thread.sleep(delay);
                        moveRandomly(walls, cols, npcs, world);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        running = false;
                    }
                }
            }
        };

        movementThread.start();
    }

    void moveRandomly(ArrayList<Rectangle> w, ArrayList<CollideObject> cols, ArrayList<NPCharacter> npcs, World world){
        if (isMovingRandomly){
            Random r = new Random();
            ArrayList<Vector2> direcs = getMoveDirections(w, cols, npcs);
            if (direcs.size()>0) {
                Vector2 avail = direcs.get(r.nextInt(direcs.size()));
                int xDir = (int)avail.x, yDir = (int)avail.y;
                animating = true;
                while (animating&&!world.changeToBattle) {
                    move(new Vector2(xDir, yDir), w, cols, npcs);
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

    ArrayList<Vector2> getMoveDirections(ArrayList<Rectangle> w, ArrayList<CollideObject> cols, ArrayList<NPCharacter> npcs){
        ArrayList<Vector2> dirs = new ArrayList<>();
        int DistanceNeeded = SPEED*maxFrames;
        for (int i = -1; i<=1; i++){
            for (int j = -1; j<=1; j++){
                Rectangle next = new Rectangle(getPosition().x + i*DistanceNeeded, getPosition().y + j*DistanceNeeded, width, height);
                Rectangle nextFoot = new Rectangle(next.x+width*.2f, next.y, width*.6f, height*.15f);
                if (canMove(nextFoot, w, cols, npcs)&&!(i==0&&j==0)){
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
        currentTexture = texture;
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
        int scale = 1;
        if (currentTexture.equals(texture)) {
            TextureRegion tr = TextureRegion.split(texture, (int) regWidth, (int) regHeight)[direction][frame];
            batch.setColor(drawTint);
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
            if (isPlayer()) {
                for (int i = 0; i < 9; i++) {
                    if (gear[i] != null) {
                        SpecialItem si = (SpecialItem) gear[i];
                        if (si.getCharacterOverlay() != null) {
                            TextureRegion t = si.getTextureRegion(direction, frame);
                            Rectangle r = new Rectangle(getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
                            batch.draw(t, r.x, r.y, r.width, r.height);
                        }
                    }
                }
            }
            batch.setColor(new Color(Color.WHITE));
        } else {
            TextureRegion tr = TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[frame/9][frame%9];
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
        }
    }

    public void render(SpriteBatch batch, float scale){
        batch.setColor(drawTint);
        if (currentTexture.equals(texture)) {
            TextureRegion tr = TextureRegion.split(texture, (int) regWidth, (int) regHeight)[direction][frame];
            batch.setColor(drawTint);
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
        } else if(currentTexture.equals(rangeAnimation)) {
            TextureRegion tr = TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[0][frame];
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
        } else if (currentTexture.equals(attackAnimation)){
            TextureRegion tr = TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[0][frame];
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
            if (frame == 3){
                tr = TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[0][4];
                batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
            }
        } else {
            TextureRegion tr = TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[0][frame];
            batch.draw(tr, getPosition().x - (width * (scale - 1) / 2) + drawOffset.x, getPosition().y + drawOffset.y, width * scale, height * scale);
        }
        batch.setColor(new Color(Color.WHITE));
    }

    public void animateRanged(boolean up, float maxTime, float time){
        currentTexture = rangeAnimation;
        float switchLen = maxTime/((rangeAnimation.getWidth()/regWidth)-4);
        if (up) {
            frame = (int) (time / switchLen);
        } else {
            float frac = time/maxTime;
            frame = frac<.25?3:(frac<.50?2:(frac<.75?1:0));
            if (time >= maxTime) {
                currentTexture = texture;
            }
        }
    }

    public void animateIdle(){
        currentTexture = attackAnimation;
        frame++;
        if (frame>1) frame = 0;
    }

    public void animateAttack(){
        currentTexture = attackAnimation;
    }

    public void animateConsume(){
        currentTexture = consumeAnimation;
    }

    public void animateDead(){
        currentTexture = deadAnimation;
        frame = 0;
        direction = 0;
    }

    public TextureRegion getImage(){
        return TextureRegion.split(currentTexture, (int)regWidth, (int)regHeight)[direction][0];
    }

    public TextureRegion getHead(){
        int smallSide = (int)Math.min(regWidth, regHeight);
        return new TextureRegion(texture, 0, (int)regHeight*direction, smallSide, smallSide);
    }

    public Texture getAttackAnimation(){
        return attackAnimation;
    }

    public Texture getRangeAnimation() { return rangeAnimation; }

    public int getDirection(){
        return direction;
    }

    public void setDirection(int dir){
        this.direction = dir;
    }

    public void setFrame(int frame){
        this.frame = frame;
    }

    public NPCharacter interact(){
        if (interactObject!=null) {
            if (interactObject.isInteractable()) {
                if (interactObject instanceof InventoryObject) {
                    InventoryObject invenObject = (InventoryObject) interactObject;
                    if (!invenObject.isOpened()) {
                        ArrayList<Item> its = invenObject.getItems();
                        for (Item i : its) {
                            if (i instanceof SpecialItem) {
                                addToInventory(new SpecialItem((SpecialItem) i));
                            } else if (i instanceof BattleItem){
                                addToInventory(new BattleItem((BattleItem) i));
                            } else {
                                addToInventory(new Item(i));
                            }
                        }
                        invenObject.setOpened(true);
                        Gdx.audio.newSound(Gdx.files.internal("audio/openInventoryObject.ogg")).play();
                    }
                } else {
                    if (interactObject.isBreakable()) {
                        SlotType bi = interactObject.getBreakItem();
                        if (bi.equals(SlotType.NORM) || (bi.equals(SlotType.WEAPON) && gear[8] != null)) {
                            interactObject.setVisible(false);
                        }
                    }
                }
            }
        } else if (interactCharacter != null){
            if (interactCharacter.isInteractable()){
                if (interactCharacter instanceof PartyCharacter){
                    if (!followers.contains(interactCharacter)) {
                        followers.add(interactCharacter);
                        interactCharacter.setInteractable(false);
                    }
                }
                interactCharacter.interact();
                if (interactCharacter.getHitBox().x<getHitBox().x){
                    interactCharacter.setDirection(1);
                    setDirection(0);
                } else {
                    interactCharacter.setDirection(0);
                    setDirection(1);
                }
                return interactCharacter;
            }
        }
        return null;
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

    public void setPosition(Vector2 position) {
        super.setPosition(position);
        footBox = new Rectangle(position.x+width*.2f, position.y, width*.6f, height*.15f);
    }

    public Rectangle getHitBox() { return new Rectangle(footBox.x, footBox.y, footBox.width, footBox.height); }

    public Item[] getInventory() {
        return WizardHelper.concat(WizardHelper.concat(inventory, gear), toolbar);
    }

    public ArrayList<BattleItem> getBattleItems(){
        ArrayList<BattleItem> items = new ArrayList<>();
        for (Item i : inventory){
            if (i instanceof BattleItem){
                items.add((BattleItem)i);
            }
        }
        for (Item i : gear){
            if (i instanceof BattleItem){
                items.add((BattleItem)i);
            }
        }
        for (Item i : toolbar){
            if (i instanceof BattleItem){
                items.add((BattleItem)i);
            }
        }
        return items;
    }

    public Item[] getToolbar(){
        return toolbar;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Character> getFollowers(){
        return followers;
    }
}
