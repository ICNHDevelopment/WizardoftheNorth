package com.icnhdevelopment.wotn.handlers;

import com.badlogic.gdx.math.Rectangle;
import com.icnhdevelopment.wotn.items.Item;

/**
 * Created by kyle on 1/23/16.
 */
public class WizardHelper {

    public static float getDistanceFromCenter(Rectangle r1, Rectangle r2){
        float center1x = r1.x + r1.width/2;
        float center1y = r1.y + r1.height/2;
        float center2x = r2.x + r2.width/2;
        float center2y = r2.y + r2.height/2;
        return (float)Math.sqrt(Math.pow(Math.abs(center1x-center2x), 2)+Math.pow(Math.abs(center1y-center2y), 2));
    }

    public static Item[] concat(Item[] a, Item[] b) {
        int aLen = a.length;
        int bLen = b.length;
        Item[] c= new Item[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }
}
