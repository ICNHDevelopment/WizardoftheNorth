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
    int tick = 0;
    float regWidth, regHeight;

    public void create(Texture tex, Vector2 position, Vector2 size, float regWidth, float regHeight){
        texture = tex;
        this.setPosition(position);
        width = (int)size.x;
        height = (int)size.y;
        drawOffset = new Vector2(0, 0);
        this.regHeight = regHeight;
        this.regWidth = regWidth;
        frame = (int)(texture.getWidth()/regWidth) - 4;
    }

    public void render(SpriteBatch batch){
        TextureRegion[][] splits = TextureRegion.split(texture, (int)regWidth, (int)regHeight);
        TextureRegion drawReg = splits[0][frame];
        batch.draw(drawReg, getPosition().x+drawOffset.x, getPosition().y+drawOffset.y, width*2, height*2);
        tick++;
        if (tick%4==0) {
            frame = Math.min(splits[0].length-2, frame + 1);
        }
    }
}
