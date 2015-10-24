package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyle on 10/23/15.
 */
public class Sprite {

    Texture texture;
    Vector2 position;
    int width, height;

    public void create(String filename, Vector2 position, Vector2 size){
        texture = new Texture(filename);
        this.position = position;
        width = (int)size.x;
        height = (int)size.y;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, position.x, position.y, width, height);
    }

    public Vector2 getPosition() { return position; }
    public Vector2 getSize() { return new Vector2(width, height); }
}
