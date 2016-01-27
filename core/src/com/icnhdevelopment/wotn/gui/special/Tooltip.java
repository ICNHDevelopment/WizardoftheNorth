package com.icnhdevelopment.wotn.gui.special;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Fonts;
import com.icnhdevelopment.wotn.items.Item;

import java.util.ArrayList;

/**
 * Created by kyle on 1/24/16.
 */
public class Tooltip {

    Texture[] sections;
    ArrayList<String> lines;
    BitmapFont font;
    boolean visible = false;
    Inventory inventory;

    public Tooltip(Inventory inventory){
        this.inventory = inventory;
        sections = new Texture[9];
        String loc = "ui/hover/hover";
        sections[0] = new Texture(loc + "TL.png");
        sections[1] = new Texture(loc + "T.png");
        sections[2] = new Texture(loc + "TR.png");
        sections[3] = new Texture(loc + "L.png");
        sections[4] = new Texture(loc + "M.png");
        sections[5] = new Texture(loc + "R.png");
        sections[6] = new Texture(loc + "BL.png");
        sections[7] = new Texture(loc + "B.png");
        sections[8] = new Texture(loc + "BR.png");
        font = Fonts.loadFont(Fonts.OPEN_SANS, 14, Color.WHITE, Color.BLACK);
    }

    public float getLongestLineLength(){
        float longest = 0;
        for (String l : lines){
            float leng = font.getBounds(l).width;
            longest = Math.max(longest, leng);
        }
        return longest+8;
    }

    public float getHeight(){
        return 20*lines.size();
    }

    public void update(Item item){
        lines = item.getTooltipData();
    }

    public void render(SpriteBatch batch){
        if (visible){
            Vector2 topRight = inventory.mousePosition;
            float width = getLongestLineLength();
            float height = getHeight();
            Rectangle TRRec = new Rectangle(topRight.x-sections[2].getWidth(), topRight.y-sections[2].getHeight(), sections[2].getWidth(), sections[2].getHeight());
            Rectangle TRec = new Rectangle(TRRec.x-width, TRRec.y, width, sections[1].getHeight());
            Rectangle TLRec = new Rectangle(TRec.x-sections[0].getWidth(), TRec.y, sections[0].getWidth(), sections[0].getHeight());
            Rectangle MRec = new Rectangle(TRRec.x-width, TRRec.y-height, width, height);
            Rectangle RRec = new Rectangle(TRRec.x, MRec.y, sections[5].getWidth(), height);
            Rectangle LRec = new Rectangle(MRec.x-sections[3].getWidth(), MRec.y, sections[3].getWidth(), height);
            Rectangle BRRec = new Rectangle(TRRec.x, MRec.y-sections[8].getHeight(), sections[8].getWidth(), sections[8].getHeight());
            Rectangle BRec = new Rectangle(BRRec.x-width, MRec.y-sections[7].getHeight(), width, sections[7].getHeight());
            Rectangle BLRec = new Rectangle(BRec.x-sections[6].getWidth(), MRec.y-sections[6].getHeight(), sections[6].getWidth(), sections[6].getHeight());
            batch.begin();
            batch.draw(sections[2], TRRec.x, TRRec.y, TRRec.width, TRRec.height);
            batch.draw(sections[1], TRec.x, TRec.y, TRec.width, TRec.height);
            batch.draw(sections[0], TLRec.x, TLRec.y, TLRec.width, TLRec.height);
            batch.draw(sections[5], RRec.x, RRec.y, RRec.width, RRec.height);
            batch.draw(sections[4], MRec.x, MRec.y, MRec.width, MRec.height);
            batch.draw(sections[3], LRec.x, LRec.y, LRec.width, LRec.height);
            batch.draw(sections[8], BRRec.x, BRRec.y, BRRec.width, BRRec.height);
            batch.draw(sections[7], BRec.x, BRec.y, BRec.width, BRec.height);
            batch.draw(sections[6], BLRec.x, BLRec.y, BLRec.width, BLRec.height);
            for (int i = 0; i<lines.size(); i++){
                String s = lines.get(i);
                font.draw(batch, s, MRec.x+4, TRec.y-4-(i*18));
            }
            batch.end();
        }
    }
}
