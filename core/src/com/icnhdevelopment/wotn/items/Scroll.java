package com.icnhdevelopment.wotn.items;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by kyle on 5/23/16.
 */
public class Scroll extends Item {

    public Scroll(Texture im) {
        super(im);
    }

    public Scroll(Scroll scroll){
        super(scroll);
    }
}
