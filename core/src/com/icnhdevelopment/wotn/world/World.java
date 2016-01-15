package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
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
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.special.Inventory;
import com.icnhdevelopment.wotn.gui.special.Toolbar;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
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

    public static int TICK = 0;

    ArrayList<Monster> enemies;
    ArrayList<Spawner> spawners;
    ArrayList<Rectangle> walls;
    ArrayList<Rectangle> overWalls;
    ArrayList<AnimatedSprite> animatedSprites;
    ArrayList<Sprite> multiDSprites;
    ArrayList<Sprite> items;

    Toolbar toolbar;
    Inventory inventory;
    boolean showInventory = false;

    public void create(String filename){
        enemies = new ArrayList<>();
        spawners = new ArrayList<>();
        walls = new ArrayList<>();
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
        //camera.translate(0, (mapProperties.get("height", Integer.class)*mapProperties.get("tileheight", Integer.class)) - (Game.HEIGHT() / SCALE));
        camera.position.x = (mapProperties.get("width", Integer.class)*TileWidth/2);
        camera.position.y = (mapProperties.get("height", Integer.class)*TileHeight) - (Game.HEIGHT());
        camera.update();
        mapRenderer.setView(camera);

        mainCharacter = new Character();
        mainCharacter.create("characters/images/MainSS.png", 7, new Vector2(10*32, (int)(16.5*32)), 5);
        multiDSprites.add(mainCharacter);

        toolbar = new Toolbar("ui/hud/ToolbarRotated.png");
        inventory = new Inventory("ui/inventory/Inventory.png");
        inventory.setVisible(false);
        inventory.setToolbar(toolbar);
    }

    void loadMap (String filename){
        TmxMapLoader tml = new TmxMapLoader();
        map = tml.load(filename);
    }

    void initMap(TiledMap m){
        loadWalls(map);
        loadSpawners(map);
        loadAnimatedSprites(map);
        loadOverwallRecs(map);
        loadItems(map);
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
        m.setRandomMovementTimer(walls);
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
        if (!showInventory) {
            if (input.isKeyDown(Input.Keys.W)) {
                mainCharacter.move(new Vector2(0, 1), walls);
            } else if (input.isKeyDown(Input.Keys.S)) {
                mainCharacter.move(new Vector2(0, -1), walls);
            } else if (input.isKeyDown(Input.Keys.A)) {
                mainCharacter.move(new Vector2(-1, 0), walls);
            } else if (input.isKeyDown(Input.Keys.D)) {
                mainCharacter.move(new Vector2(1, 0), walls);
            } else {
                mainCharacter.animate(false);
            }
            mainCharacter.updateWalls(map, overWalls);

            for (Spawner s : spawners) {
                if (s.spawn) {
                    s.spawn();
                    s.spawn = false;
                }
            }
            sortMultiDSprites();

            TICK++;
            if (input.isKeyDown(Input.Keys.E)) {
                showInventory = true;
                inventory.setVisible(true);
            }
        }
        else {
            inventory.update(input);
            if (input.isKeyDown(Input.Keys.ESCAPE)){
                showInventory = false;
                inventory.setVisible(false);
            }
        }
    }

    void sortMultiDSprites(){
        boolean madeChange = true;
        while (madeChange){
            madeChange = false;
            for (int i = 0; i<multiDSprites.size()-1; i++){
                if (multiDSprites.get(i).getHitbox().y<multiDSprites.get(i+1).getHitbox().y){
                    Sprite s = multiDSprites.get(i);
                    multiDSprites.set(i, multiDSprites.get(i+1));
                    multiDSprites.set(i+1, s);
                    madeChange = true;
                }
            }
        }
    }

    public void render(SpriteBatch batch){
        camera.position.x = mainCharacter.getPosition().x;
        camera.position.y = mainCharacter.getPosition().y;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        mapRenderer.render(new int[]{0});
        if (map.getLayers().get(1).getOpacity()==1) {
            mapRenderer.render(new int[]{1});
        }
        batch.begin();
        for (AnimatedSprite as : animatedSprites){
            as.render(batch);
        }
        for (Sprite s : items){
            s.render(batch);
        }
        for (Sprite m : multiDSprites){
            m.render(batch);
        }
        batch.end();
        if (map.getLayers().get(1).getOpacity()<1) {
            mapRenderer.render(new int[]{1});
        }

        Texture temp = new Texture("ui/hud/HUD.png");
        batch.begin();
        batch.setProjectionMatrix(inventory.getRenderCam().combined);
        batch.draw(temp, 0, Game.HEIGHT()-temp.getHeight()*3, temp.getWidth()*3, temp.getHeight()*3);
        batch.end();

        toolbar.render(batch);

        if (showInventory) {
            batch.setProjectionMatrix(inventory.getRenderCam().combined);
            inventory.render(batch);
        }
    }

}
