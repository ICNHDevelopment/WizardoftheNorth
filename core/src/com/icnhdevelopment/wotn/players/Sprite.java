package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.io.Serializable;

/**
 * Created by kyle on 10/23/15.
 */
public class Sprite implements Serializable {

    protected Texture texture;
    protected Vector2 position;
    protected Vector2 drawOffset;
    protected int width, height;
    protected transient Color drawTint = new Color(Color.WHITE);

    public void create(String filename, Vector2 position, Vector2 size){
        texture = new Texture(filename);
        this.setPosition(position);
        width = (int)size.x;
        height = (int)size.y;
        drawOffset = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch){
        batch.setColor(drawTint);
        batch.draw(texture, getPosition().x+drawOffset.x, getPosition().y+drawOffset.y, width, height);
        batch.setColor(new Color(Color.WHITE));
    }

    public void setDrawTint(Color col) { drawTint = col; }
    public Vector2 getPosition() { return position; }
    public Vector2 getSize() { return new Vector2(width, height); }
    public Rectangle getHitBox() { return new Rectangle(getPosition().x, getPosition().y, width, height); }
    public void changeDrawOffset(Vector2 deltaOffset) { drawOffset.x += deltaOffset.x; drawOffset.y += deltaOffset.y; }
    public void setDrawOffset(Vector2 offset) { drawOffset.x = offset.x; drawOffset.y = offset.y; }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
