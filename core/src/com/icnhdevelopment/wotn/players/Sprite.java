package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyle on 10/23/15.
 */
public class Sprite {

    protected Texture texture;
    protected Vector2 position;
    protected int width, height;

    public void create(String filename, Vector2 position, Vector2 size){
        texture = new Texture(filename);
        this.setPosition(position);
        width = (int)size.x;
        height = (int)size.y;
    }

    public void render(SpriteBatch batch){
        batch.draw(texture, getPosition().x, getPosition().y, width, height);
    }

    public Vector2 getPosition() { return position; }
    public Vector2 getSize() { return new Vector2(width, height); }
    public Rectangle getHitBox() { return new Rectangle(getPosition().x, getPosition().y, width, height); }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
