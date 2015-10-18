package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    public static double SCALE = 1.4;
    Character mainCharacter;
    public static int TileWidth, TileHeight;

    public static int TICK = 0;

    ArrayList<Monster> enemies;
    ArrayList<Spawner> spawners;
    ArrayList<Rectangle> walls;

    public void create(String filename){
        enemies = new ArrayList<>();
        spawners = new ArrayList<>();
        walls = new ArrayList<>();

        loadMap(filename);
        mapProperties = map.getProperties();
        TileWidth = mapProperties.get("tilewidth", Integer.class);
        TileHeight = mapProperties.get("tileheight", Integer.class);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (int)(Game.WIDTH()/SCALE), (int)(Game.HEIGHT()/SCALE));
        //camera.translate(0, (mapProperties.get("height", Integer.class)*mapProperties.get("tileheight", Integer.class)) - (Game.HEIGHT() / SCALE));
        camera.position.x = (mapProperties.get("width", Integer.class)*TileWidth/2);
        camera.position.y = (mapProperties.get("height", Integer.class)*TileHeight) - (Game.HEIGHT());
        camera.update();
        mapRenderer.setView(camera);

        mainCharacter = new Character();
        mainCharacter.create("characters/images/MainSS.png", 7, new Vector2(26*32, 22*32));
    }

    void loadMap (String filename){
        TmxMapLoader tml = new TmxMapLoader();
        map = tml.load(filename);
        loadWalls(map);
        loadSpawners(map);
    }

    void loadWalls(TiledMap m){
        MapLayer layer = m.getLayers().get("walls");
        MapObjects objs = layer.getObjects();
        for (MapObject obj : objs) {
            String type = (String) obj.getProperties().get("type");
            float tx = (float) obj.getProperties().get("x");
            float ty = (float) obj.getProperties().get("y");
            float tw = (float) obj.getProperties().get("width");
            float th = (float) obj.getProperties().get("height");
            walls.add(new Rectangle(tx, ty, tw, th));
        }
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

    public void spawn(Monster m){
        enemies.add(m);
    }

    public void update(CInputProcessor input){
        if (input.isKeyDown(Input.Keys.W)){
            mainCharacter.move(new Vector2(0, 1), walls);
        }
        else if (input.isKeyDown(Input.Keys.S)){
            mainCharacter.move(new Vector2(0, -1), walls);
        }
        else if (input.isKeyDown(Input.Keys.A)){
            mainCharacter.move(new Vector2(-1, 0), walls);
        }
        else if (input.isKeyDown(Input.Keys.D)){
            mainCharacter.move(new Vector2(1, 0), walls);
        }
        TICK++;
        for (Spawner s : spawners){
            if (s.spawn){
                s.spawn();
                s.spawn = false;
            }
        }
    }

    public void render(SpriteBatch batch){
        camera.position.x = mainCharacter.getPosition().x;
        camera.position.y = mainCharacter.getPosition().y;
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        mapRenderer.setView(camera);
        mapRenderer.render();
        batch.begin();
        mainCharacter.render(batch);
        for (Monster m : enemies){
            m.render(batch);
        }
        batch.end();
    }

}
