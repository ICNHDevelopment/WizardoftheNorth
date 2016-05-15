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
import com.icnhdevelopment.wotn.players.*;
import com.icnhdevelopment.wotn.players.Character;

import java.util.ArrayList;

/**
 * Created by kyle on 6/4/15.
 */
public class World {

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
    ArrayList<Sprite> multiDSprites;
    ArrayList<Sprite> items;

    Toolbar toolbar;
    Inventory inventory;
    Hud hud;

    public boolean changeToBattle = false;
    Texture battleTransition;
    int battleStage = -1;
    Character battleChar;

    String state = "fadein";
    float alpha = 1f;

    public void create(String filename){
        fileLocation = filename.substring(0, filename.lastIndexOf("/")+1);
        enemies = new ArrayList<>();
        spawners = new ArrayList<>();
        walls = new ArrayList<>();
        collideObjects = new ArrayList<>();
        inventoryObjects = new ArrayList<>();
        overWalls = new ArrayList<>();
        animatedSprites = new ArrayList<>();
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
        mainCharacter.create("characters/images/MainSS.png", 7, new Vector2(10*32, (int)(16.5*32)), 5, true, true);
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
            mon.create(mon.defaultFile, mon.defaultMaxFrames, new Vector2(tx, ty), 2, false, false);
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
            String file = "world/images/" + name + "SS.png";
            AnimatedSprite temp = new AnimatedSprite();
            temp.create(file, f, new Vector2(tx+tw/2, ty+th/2), new Vector2(tw, th), 8);
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
            String file = "Items/" + name + ".png";
            Sprite temp = new Sprite();
            temp.create(file, new Vector2(tx, ty), new Vector2(tw, th));
            items.add(temp);
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

    public void spawn(Monster m){
        enemies.add(m);
        multiDSprites.add(m);
        m.setRandomMovementTimer(walls, collideObjects, this);
    }

    public void kill(Character c){
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
                Game.soundHandler.PlaySoundLooping(Gdx.audio.newSound(Gdx.files.internal("audio/sewerMusic.wav")), .1f);
            }
        }else {
            if (changeToBattle) {
                if (TICK % 9 == 0 && battleStage < 7) {
                    battleStage++;
                }
                TICK++;
                if (battleStage == 7 && TICK%9==8) {
                    BattleInfo bi = new BattleInfo(mainCharacter, battleChar);
                    bi.setBackFile(fileLocation + "BattleScene.png");
                    bi.setBattleTex(battleTransition);
                    bi.setWorld(this);
                    bi.setCharacterWorldPosition(new Vector2(mainCharacter.getPosition().x, mainCharacter.getPosition().y));
                    bi.setEnemy(battleChar);
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
            } else {
                battleChar = null;
                if (battleStage > -1 && TICK % 9 == 0) {
                    battleStage--;
                }
                if (input.isKeyDown(Input.Keys.W)) {
                    mainCharacter.move(new Vector2(0, 1), walls, collideObjects);
                } else if (input.isKeyDown(Input.Keys.S)) {
                    mainCharacter.move(new Vector2(0, -1), walls, collideObjects);
                } else if (input.isKeyDown(Input.Keys.A)) {
                    mainCharacter.move(new Vector2(-1, 0), walls, collideObjects);
                } else if (input.isKeyDown(Input.Keys.D)) {
                    mainCharacter.move(new Vector2(1, 0), walls, collideObjects);
                } else {
                    mainCharacter.animate(false);
                }
                mainCharacter.updateWalls(map, overWalls);
                mainCharacter.updateInteractObjects(collideObjects);
                mainCharacter.heal(1f/300f);
                if (input.isKeyDown(Input.Keys.F)){
                    mainCharacter.interact();
                }

                for (Spawner s : spawners) {
                    if (s.spawn) {
                        s.spawn();
                        s.spawn = false;
                    }
                }
                sortMultiDSprites();
                testForBattle();

                TICK++;
                if (input.isKeyDown(Input.Keys.E)) {
                    inventory.setVisible(true);
                }
                if (input.isKeyDown(Input.Keys.T)) {
                    changeToBattle = true;
                    battleStage = 0;
                }
            }
        }
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
                Game.soundHandler.PlaySoundLooping(Gdx.audio.newSound(Gdx.files.internal("audio/battleMusic.wav")), .1f);
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
        batch.begin();
        for (CollideObject c : collideObjects){
            c.render(batch);
        }
        for (AnimatedSprite as : animatedSprites){
            as.render(batch);
        }
        for (Sprite s : items){
            s.render(batch);
        }
        for (Sprite m : multiDSprites){
            if (!m.equals(mainCharacter)||map.getLayers().get(1).getOpacity()<1) {
                m.render(batch);
            }
        }
        batch.end();
        mapRenderer.render(new int[]{1});
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Sprite m : multiDSprites){
            if (m.equals(mainCharacter)&&map.getLayers().get(1).getOpacity()==1) {
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