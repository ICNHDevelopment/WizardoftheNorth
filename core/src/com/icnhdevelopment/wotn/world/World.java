package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.players.*;
import com.icnhdevelopment.wotn.players.Character;

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

    public void create(String filename){
        TmxMapLoader tml = new TmxMapLoader();
        map = tml.load(filename);
        mapProperties = map.getProperties();
        TileWidth = mapProperties.get("tilewidth", Integer.class);
        TileHeight = mapProperties.get("tileheight", Integer.class);
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, (int)(Game.WIDTH()/SCALE), (int)(Game.HEIGHT()/SCALE));
        //camera.translate(0, (mapProperties.get("height", Integer.class)*mapProperties.get("tileheight", Integer.class)) - (Game.HEIGHT() / SCALE));
        camera.position.x = (mapProperties.get("width", Integer.class)*TileWidth/2);
        camera.position.y =  (mapProperties.get("height", Integer.class)*TileHeight) - (Game.HEIGHT());
        camera.update();
        mapRenderer.setView(camera);

        mainCharacter = new Character();
        mainCharacter.create("characters/images/MainSS.png", 7);
    }

    public void update(CInputProcessor input){
        if (input.isKeyDown(Input.Keys.W)){
            if (!mainCharacter.isAnimating()){
                mainCharacter.animate();
            }
        }
    }

    public void render(SpriteBatch batch){
        camera.update();
        mapRenderer.render();
        batch.setProjectionMatrix(camera.projection);
        batch.begin();
        mainCharacter.render(batch);
        batch.end();
    }

}
