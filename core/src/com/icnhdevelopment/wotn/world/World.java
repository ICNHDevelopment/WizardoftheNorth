package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.battle.BattleInfo;
import com.icnhdevelopment.wotn.gui.special.*;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.GameState;
import com.icnhdevelopment.wotn.handlers.TextHandler;
import com.icnhdevelopment.wotn.handlers.WizardHelper;
import com.icnhdevelopment.wotn.items.Item;
import com.icnhdevelopment.wotn.items.Scroll;
import com.icnhdevelopment.wotn.players.*;
import com.icnhdevelopment.wotn.players.Character;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by kyle on 6/4/15.
 */
public class World implements Serializable{

    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;
    TiledMap map;
    MapProperties mapProperties;
    public static double SCALE = 2;
    Character mainCharacter;
    public static int TileWidth, TileHeight;
    String fileLocation;

    public static int TICK = 0;

    ArrayList<Monster> enemies;
    ArrayList<Spawner> spawners;
    ArrayList<Rectangle> walls;
    ArrayList<CollideObject> collideObjects;
    ArrayList<InventoryObject> inventoryObjects;
    ArrayList<Rectangle> overWalls;
    ArrayList<AnimatedSprite> animatedSprites;
    ArrayList<NPCharacter> npcs;
    ArrayList<Sprite> multiDSprites;
    ArrayList<Item> items;

    Toolbar toolbar;
    Inventory inventory;
    Hud hud;

    public boolean changeToBattle = false;
    Texture battleTransition;
    int battleStage = -1;
    Character battleChar;

    String state = "fadein";
    String stateState = "normal";
    float alpha = 1f;

    TextHandler dialogueScroller;
    Texture dialogueBox;
    Rectangle dialogueRectangle;
    NPCharacter speakingCharacter;

    public void create(String filename){
        fileLocation = filename.substring(0, filename.lastIndexOf("/")+1);
        enemies = new ArrayList<>();
        spawners = new ArrayList<>();
        walls = new ArrayList<>();
        collideObjects = new ArrayList<>();
        inventoryObjects = new ArrayList<>();
        overWalls = new ArrayList<>();
        animatedSprites = new ArrayList<>();
        npcs = new ArrayList<>();
        multiDSprites = new ArrayList<>();
        items = new ArrayList<>();

        loadMap(filename);
        mapProperties = map.getProperties();
        TileWidth = mapProperties.get("tilewidth", Integer.class);
        TileHeight = mapProperties.get("tileheight", Integer.class);
        initMap(map);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (int)(Game.WIDTH()/SCALE), (int)(Game.HEIGHT()/SCALE));
        camera.position.x = (mapProperties.get("width", Integer.class)*TileWidth/2);
        camera.position.y = (mapProperties.get("height", Integer.class)*TileHeight) - (Game.HEIGHT());
        camera.update();
        mapRenderer.setView(camera);

        mainCharacter = new Character();
        mainCharacter.create("characters/images/", "Main", 7, new Vector2(10*32, (int)(16.5*32)), 5, true, true);
        multiDSprites.add(mainCharacter);

        toolbar = new Toolbar("ui/hud/ToolbarRotated.png");
        toolbar.setVisible(true);
        toolbar.setCharacter(mainCharacter);
        inventory = new Inventory("ui/inventory/Inventory.png");
        inventory.createFont();
        inventory.setVisible(false);
        inventory.setToolbar(toolbar);
        inventory.setCharacter(mainCharacter);
        hud = new Hud(mainCharacter);

        battleTransition = new Texture("ui/hud/toBlack.png");

        dialogueBox = new Texture("ui/hud/speechPopup.png");
        dialogueRectangle = new Rectangle((Game.WIDTH()-dialogueBox.getWidth()*2)/2 + dialogueBox.getHeight()*2, dialogueBox.getHeight(), dialogueBox.getWidth()*2 - dialogueBox.getHeight()*2, dialogueBox.getHeight()*2);
    }

    void loadMap (String filename){
        TmxMapLoader tml = new TmxMapLoader();
        map = tml.load(filename);
    }

    void initMap(TiledMap m){
        loadWalls(m);
        loadCollideObjects(m);
        loadInventoryObjects(m);
        loadSpawners(m);
        loadPreSpawns(m);
        loadAnimatedSprites(m);
        loadNPCS(m);
        loadOverwallRecs(m);
        loadItems(m);
    }

    void loadWalls(TiledMap m){
        MapLayer layer = m.getLayers().get("walls");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs) {
            float tx = (float) obj.getProperties().get("x");
            float ty = (float) obj.getProperties().get("y");
            float tw = (float) obj.getProperties().get("width");
            float th = (float) obj.getProperties().get("height");
            walls.add(new Rectangle(tx, ty, tw, th));
        }
        smoothRecs(walls);
    }

    void loadSpawners(TiledMap m){
        MapLayer layer = m.getLayers().get("spawners");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs){
            String type = (String)obj.getProperties().get("type");
            float tx = (float)obj.getProperties().get("x");
            float ty = (float)obj.getProperties().get("y");
            Spawner s = new Spawner(Monster.getMonster(type), this, new Vector2(tx, ty));
            spawners.add(s);
            s.start();
        }
    }

    void loadPreSpawns(TiledMap m){
        MapLayer layer = m.getLayers().get("preSpawns");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs){
            String[] data = ((String)obj.getProperties().get("type")).split(":");
            String type = data[0];
            String battleDataFile = data[1];
            float tx = (float)obj.getProperties().get("x");
            float ty = (float)obj.getProperties().get("y");
            Monster mon = Monster.getMonster(type);
            assert mon != null;
            mon.battleDataFile = fileLocation + battleDataFile + ".txt";
            mon.create(mon.defaultFile, mon.prefix, mon.defaultMaxFrames, new Vector2(tx, ty), 2, false, false);
            this.spawn(mon);
        }
    }

    void loadAnimatedSprites(TiledMap m){
        MapLayer layer = m.getLayers().get("objectF");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs) {
            String frames = (String) obj.getProperties().get("type");
            int f = Integer.parseInt(frames);
            String name = obj.getName();
            float tx = (float) obj.getProperties().get("x");
            float ty = (float) obj.getProperties().get("y");
            float tw = (float) obj.getProperties().get("width");
            float th = (float) obj.getProperties().get("height");
            String file = "world/images/";
            AnimatedSprite temp = new AnimatedSprite();
            temp.create(file, name, f, new Vector2(tx+tw/2, ty+th/2), new Vector2(tw, th), 8);
            animatedSprites.add(temp);
        }
    }

    void loadCollideObjects(TiledMap m){
        MapLayer layer = m.getLayers().get("collideObjs");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs) {
            String[] data = ((String)obj.getProperties().get("type")).split(":");
            String name = data[0];
            boolean brk = Boolean.valueOf(data[1]);
            SlotType slt = SlotType.valueOf(data[2]);
            float tx = (float) obj.getProperties().get("x");
            float ty = (float) obj.getProperties().get("y");
            float tw = (float) obj.getProperties().get("width");
            float th = (float) obj.getProperties().get("height");
            String file = "world/images/" + name + ".png";
            CollideObject co = new CollideObject();
            co.create(file, new Vector2(tx, ty), new Vector2(tw, th), brk, slt);
            collideObjects.add(co);
        }
    }

    void loadInventoryObjects(TiledMap m){
        MapLayer layer = m.getLayers().get("inventories");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs) {
            String[] data = ((String)obj.getProperties().get("type")).split(":");
            String name = data[0];
            String flnm = data[1];
            boolean brk = Boolean.valueOf(data[2]);
            SlotType slt = SlotType.valueOf(data[3]);
            float tx = (float) obj.getProperties().get("x");
            float ty = (float) obj.getProperties().get("y");
            float tw = (float) obj.getProperties().get("width");
            float th = (float) obj.getProperties().get("height");
            String file = "world/images/" + name + ".png";
            InventoryObject co = new InventoryObject();
            co.create(file, new Vector2(tx, ty), new Vector2(tw, th), brk, slt, name, fileLocation + flnm);
            collideObjects.add(co);
            inventoryObjects.add(co);
        }
    }

    void loadItems(TiledMap m){
        MapLayer layer = m.getLayers().get("items");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs) {
            String name = (String) obj.getProperties().get("type");
            float tx = (float) obj.getProperties().get("x");
            float ty = (float) obj.getProperties().get("y");
            float tw = (float) obj.getProperties().get("width");
            float th = (float) obj.getProperties().get("height");
            Rectangle rec = new Rectangle(tx, ty, tw, th);
            Item i = Item.GetItemByName(name);
            if (i!=null) {
                items.add(new Item(i, rec));
            }
        }
    }

    void loadOverwallRecs(TiledMap m){
        MapLayer layer = m.getLayers().get("overwallRecs");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs){
            float tx = (float)obj.getProperties().get("x");
            float ty = (float)obj.getProperties().get("y");
            float tw = (float) obj.getProperties().get("width");
            float th = (float) obj.getProperties().get("height");
            overWalls.add(new Rectangle(tx, ty, tw, th));
        }
        smoothRecs(overWalls);
    }

    void loadNPCS(TiledMap m){
        MapLayer layer = m.getLayers().get("npcs");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs){
            String[] data = ((String)obj.getProperties().get("type")).split(":");
            String name = data[0];
            boolean partyable = data[1].equals("true");
            float tx = (float)obj.getProperties().get("x");
            float ty = (float)obj.getProperties().get("y");
            NPCharacter tempChar = partyable ? new PartyCharacter(data[2]) : new NPCharacter(data[2]);
            int maxFrames = 7;
            if (!partyable){
                maxFrames = 1;
            }
            tempChar.create(tempChar.defaultFile, tempChar.prefix, maxFrames, new Vector2(tx, ty), 5, false, false, name);
            npcs.add(tempChar);
            multiDSprites.add(tempChar);
        }
    }

    public void spawn(Monster m){
        enemies.add(m);
        multiDSprites.add(m);
        if (!(m instanceof BossSpider)) {
            m.setRandomMovementTimer(walls, collideObjects, this, npcs);
        }
    }

    public void kill(Character c){
        if (c instanceof Monster){
            Monster m = (Monster)c;
            ArrayList<Item> droppedItems = m.getDrops();
            if (droppedItems!=null){
                InventoryObject io = new InventoryObject();
                io.create("world/images/Bag.png", new Vector2(c.getPosition()), new Vector2(32, 32), true, SlotType.NORM, "Bag");
                for (Item i : droppedItems){
                    io.addItem(i);
                }
                collideObjects.add(io);
                inventoryObjects.add(io);
            }
        }
        if (enemies.contains(c)){
            enemies.remove(c);
        }
        if (multiDSprites.contains(c)){
            multiDSprites.remove(c);
        }
    }

    void smoothRecs(ArrayList<Rectangle> recs){
        for (int i =0; i<recs.size(); i++){
            Rectangle r = recs.get(i);
            r.x = Math.round(r.x / TileWidth)*TileWidth;
            r.y = Math.round(r.y/TileHeight)*TileHeight;
            r.width = Math.round(r.width/TileWidth)*TileWidth;
            r.height = Math.round(r.height/TileHeight)*TileHeight;
        }
    }

    public void update(CInputProcessor input){
        if (state.equals("fadein")){
            alpha -= .02f;
            if (alpha<=0){
                state="";
                Game.soundHandler.PlaySoundLooping(Gdx.audio.newSound(Gdx.files.internal("audio/sewerMusic.ogg")), .1f);
            }
        }else {
            if (changeToBattle) {
                if (TICK % 9 == 0 && battleStage < 7) {
                    battleStage++;
                }
                TICK++;
                if (battleStage == 7 && TICK % 9 == 8) {
                    BattleInfo bi = new BattleInfo(mainCharacter, battleChar);
                    bi.setBackFile(fileLocation + "BattleScene.png");
                    bi.setBattleTex(battleTransition);
                    bi.setWorld(this);
                    bi.setCharacterWorldPosition(new Vector2(mainCharacter.getPosition().x, mainCharacter.getPosition().y));
                    changeToBattle = false;
                    battleStage = -1;
                    state = "fadein";
                    alpha = 1;
                    Game.currentBattle = new Battle();
                    Game.currentBattle.create(bi);
                    Game.GAME_STATE = GameState.BATTLE;
                }
            } else if (inventory.isVisible()) {
                inventory.update(input);
                inventory.updateChildren(input);
                if (input.isKeyDown(Input.Keys.ESCAPE)) {
                    inventory.setVisible(false);
                }
            } else if (stateState.equals("dialogue")){
                //Yada yada. Rada rada!
                if (TICK%4==0) {
                    dialogueScroller.update();
                }
                if (input.didMouseClick()){
                    if (dialogueScroller.isTextFinished()){
                        stateState = "normal";
                    } else if (dialogueScroller.isFrameFinished()){
                        dialogueScroller.goToNextFrame();
                    } else {
                        dialogueScroller.skipToEnd();
                    }
                }
                TICK++;
            }else {
                battleChar = null;
                if (battleStage > -1 && TICK % 9 == 0) {
                    battleStage--;
                }
                if (input.isKeyDown(Input.Keys.W)) {
                    mainCharacter.move(new Vector2(0, 1), walls, collideObjects, npcs);
                    mainCharacter.updateFollowers(true);
                } else if (input.isKeyDown(Input.Keys.S)) {
                    mainCharacter.move(new Vector2(0, -1), walls, collideObjects, npcs);
                    mainCharacter.updateFollowers(true);
                } else if (input.isKeyDown(Input.Keys.A)) {
                    mainCharacter.move(new Vector2(-1, 0), walls, collideObjects, npcs);
                    mainCharacter.updateFollowers(true);
                } else if (input.isKeyDown(Input.Keys.D)) {
                    mainCharacter.move(new Vector2(1, 0), walls, collideObjects, npcs);
                    mainCharacter.updateFollowers(true);
                } else {
                    mainCharacter.animate(false);
                    mainCharacter.updateFollowers(false);
                }
                mainCharacter.updateWalls(map, overWalls);
                mainCharacter.updateInteractObjects(collideObjects);
                mainCharacter.updateNPCS(npcs);
                mainCharacter.heal(1f/300f);
                mainCharacter.remember(1f/300f);
                if (input.isKeyDown(Input.Keys.F)){
                    NPCharacter inter = mainCharacter.interact();
                    if (inter!=null){
                        stateState = "dialogue";
                        dialogueScroller = new TextHandler(inter.getName() + ": " + inter.getCurrentDialogue(), dialogueRectangle);
                        speakingCharacter = inter;
                    }
                }
                for (Spawner s : spawners) {
                    if (s.spawn) {
                        s.spawn();
                        s.spawn = false;
                    }
                }
                sortMultiDSprites();
                testForBattle();
                if (input.isKeyDown(Input.Keys.NUM_1)){
                    Item[] sc = mainCharacter.getScrolls();
                    if (sc[0] != null){
                        Item closest = null;
                        float least = Float.MAX_VALUE;
                        for (Item i : items) {
                            float dist = WizardHelper.getDistanceFromPoint(mainCharacter.getPosition(), i.getPosition());
                            if (dist < least) {
                                least = dist;
                                closest = i;
                            }
                        }
                        if (closest != null) {
                            Vector2 iPos = closest.getPosition();
                            if (Math.abs(iPos.x - mainCharacter.getPosition().x) < Game.WIDTH() / 2) {
                                if (Math.abs(iPos.y - mainCharacter.getPosition().y) < Game.HEIGHT() / 2) {
                                    mainCharacter.addToInventory(new Item(closest));
                                    items.remove(closest);
                                }
                            }
                        }
                    }
                }

                TICK++;
                if (input.isKeyDown(Input.Keys.E)) {
                    inventory.setVisible(true);
                    mainCharacter.setFrame(1);
                }
            }
        }
    }

    boolean isUnderOverWall(Sprite s){
        for (Rectangle r : overWalls){
            if (r.overlaps(s.getHitBox())){
                return true;
            }
        }
        return false;
    }

    void sortMultiDSprites(){
        boolean madeChange = true;
        while (madeChange){
            madeChange = false;
            for (int i = 0; i<multiDSprites.size()-1; i++){
                if (multiDSprites.get(i).getHitBox().y<multiDSprites.get(i+1).getHitBox().y){
                    Sprite s = multiDSprites.get(i);
                    multiDSprites.set(i, multiDSprites.get(i+1));
                    multiDSprites.set(i+1, s);
                    madeChange = true;
                }
            }
        }
    }

    void testForBattle(){
        for (Monster m : enemies){
            if (m.getHitBox().overlaps(mainCharacter.getHitBox())){
                changeToBattle = true;
                battleChar = m;
                mainCharacter.setFrame(0);
                mainCharacter.setDirection(0);
                Game.soundHandler.PlaySoundLooping(Gdx.audio.newSound(Gdx.files.internal("audio/battleMusic.ogg")), .1f);
                return;
            }
        }
    }

    public void render(SpriteBatch batch){
        camera.position.x = mainCharacter.getPosition().x + mainCharacter.getSize().x/2;
        camera.position.y = mainCharacter.getPosition().y + mainCharacter.getSize().y/2;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{0});
        try {
            mapRenderer.render(new int[]{2});
        } catch (Exception e){}
        batch.begin();
        for (CollideObject c : collideObjects){
            c.render(batch);
        }
        for (AnimatedSprite as : animatedSprites){
            as.render(batch);
        }
        for (Item s : items){
            s.render(batch);
        }
        for (Sprite m : multiDSprites){
            if (isUnderOverWall(m)){
                if (map.getLayers().get(1).getOpacity()<1) {
                    m.render(batch);
                }
            }else{
                m.render(batch);
            }
        }
        batch.end();
        mapRenderer.render(new int[]{1});
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Sprite m : multiDSprites){
            if (!isUnderOverWall(m)){
                m.render(batch);
            }
        }
        batch.end();

        batch.setProjectionMatrix(inventory.getRenderCam().combined);
        hud.render(batch);

        batch.setProjectionMatrix(inventory.getRenderCam().combined);
        toolbar.render(batch, mainCharacter.getToolbar());

        if (inventory.isVisible()) {
            batch.setProjectionMatrix(inventory.getRenderCam().combined);
            inventory.render(batch, mainCharacter.getInventory());
        }
        if (stateState.equals("dialogue")){
            //Render player and speaking character later
            batch.begin();
            batch.draw(dialogueBox, dialogueRectangle.x - dialogueRectangle.height, dialogueRectangle.y, dialogueRectangle.width + dialogueRectangle.height, dialogueRectangle.height);
            dialogueScroller.render(batch);
            batch.draw(speakingCharacter.getHead(), dialogueRectangle.x - dialogueRectangle.height + 4, dialogueRectangle.y+4, dialogueRectangle.height-8, dialogueRectangle.height-8);
            batch.end();
        }
        if (battleStage>-1){
            batch.setProjectionMatrix(inventory.getRenderCam().combined);
            batch.begin();
            TextureRegion tr = new TextureRegion(battleTransition, ((battleStage%4)*160), (int)Math.floor((battleStage/4)*90), 160, 90);
            batch.draw(tr, 0, 0, Game.WIDTH(), Game.HEIGHT());
            batch.end();
            batch.setProjectionMatrix(camera.combined);
            batch.begin();
            mainCharacter.render(batch);
            battleChar.render(batch);
            batch.end();
        }
        if (state.equals("fadein")){
            batch.setProjectionMatrix(inventory.getRenderCam().combined);
            batch.begin();
            Texture black = new Texture("ui/hud/ExperienceMeter.png");
            Color c = batch.getColor();
            Color b = Color.BLACK;
            batch.setColor(b.r, b.g, b.b, alpha);
            batch.draw(black, 0, 0, Game.WIDTH(), Game.HEIGHT());
            batch.setColor(c);
            batch.end();
        }
    }

}