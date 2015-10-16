package com.icnhdevelopment.wotn.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.icnhdevelopment.wotn.Game;

/**
 * Created by kyle on 6/4/15.
 */
public class World {

    OrthographicCamera camera;
    TiledMapRenderer mapRenderer;
    TiledMap map;
    MapProperties mapProperties;
    int SCALE = 2;

    public void create(String filename){
        TmxMapLoader tml = new TmxMapLoader();
        map = tml.load(filename);
        mapProperties = map.getProperties();
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Game.WIDTH()/SCALE, Game.HEIGHT()/SCALE);
        //camera.translate(0, (mapProperties.get("height", Integer.class)*mapProperties.get("tileheight", Integer.class)) - (Game.HEIGHT() / SCALE));
        camera.position.x = (mapProperties.get("width", Integer.class)*mapProperties.get("tilewidth", Integer.class)/2);
        camera.position.y =  (mapProperties.get("height", Integer.class)*mapProperties.get("tileheight", Integer.class)) - (Game.HEIGHT());
        camera.update();
        mapRenderer.setView(camera);
    }

    public void render(){
        camera.update();
        mapRenderer.render();
    }

}
