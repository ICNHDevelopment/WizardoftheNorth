package com.icnhdevelopment.wotn.players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyle on 5/16/16.
 */
public class Projectile extends Sprite {

    int frame = 0;

    public void create(Texture tex, Vector2 position, Vector2 size){
        texture = tex;
        this.setPosition(position);
        width = (int)size.x;
        height = (int)size.y;
        drawOffset = new Vector2(0, 0);
    }

    public void render(SpriteBatch batch){
        TextureRegion drawReg = TextureRegion.split(texture, texture.getWidth()/9, texture.getHeight()/2)[1][frame+4];
        batch.draw(drawReg, getPosition().x+drawOffset.x, getPosition().y+drawOffset.y, width*2, height*2);
        frame = Math.min(2, frame+1);
    }
}
