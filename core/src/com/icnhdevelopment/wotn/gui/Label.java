package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyle on 5/31/15.
 */
public class Label extends Container {
    String text;
    int fontSize = 12;
    Color color = Color.WHITE;
    Color borderColor = Color.WHITE;
    /**
     * hAlignment determines the horizontal alignment. Should be LEFT, CENTER, or RIGHT
     * vAlignment determines the vertical alignment. Should be TOP, MIDDLE, or BOTTOM
     */
    Alignment hAlignment = Alignment.LEFT;
    Alignment vAlignment = Alignment.BOTTOM;
    String fontType = Fonts.OPEN_SANS;
    BitmapFont font;
    int fontX = 0, fontY = 0;
    float fontWidth, fontHeight;

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    public void sethAlignment(Alignment hAlignment) {
        this.hAlignment = hAlignment;
    }

    public void setvAlignment(Alignment vAlignment) {
        this.vAlignment = vAlignment;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public Label(Container pa, Vector2 pos, Vector2 sz, String text) {
        super(pa, pos, sz);

        this.text = text;
    }

    /**
     * Loads the font and sets the position inside the Container based on the Alignments.
     * Since LEFT and BOTTOM are default we can ignore those cases.
     * Remember that (0, 0) is bottom left, so bottom and left should be default.
     */
    public void createFont() {
        font = Fonts.loadFont(Fonts.OPEN_SANS, fontSize, color, borderColor);
        BitmapFont.TextBounds bounds = font.getBounds(text);
        fontWidth = bounds.width;
        fontHeight = bounds.height;

        //<editor-fold desc="Alignments">
        if (hAlignment == Alignment.CENTER) { //Centered horizontally
            fontX = (int)(super.position.x + (super.size.x-fontWidth)/2);
        }
        else if (hAlignment == Alignment.RIGHT) {
            fontX = (int)(super.position.x + super.size.x - fontWidth);
        }
        if (vAlignment == Alignment.MIDDLE) { //Centered vertically
            fontY = (int)(super.position.y + (super.size.y-fontHeight)/2);
        }
        else if (vAlignment == Alignment.TOP) {
            fontY = (int)(super.position.y + super.size.y - fontHeight);
        }
        //</editor-fold>

    }

    @Override
    public void render(SpriteBatch batch) {
        /* Todo: Add render text code
        * Probably will have to implement getAbsolutePosition() here
        */
        if (font == null) {
            this.createFont();
        }
        float yOffset = fontHeight, xOffset = 0;
        if (color != borderColor) {
            yOffset += fontSize/12;
            xOffset += fontSize/12;
        }
        if (visible) {
            Vector2 temp = getAbsolutePosition();
            font.draw(batch, text, temp.x + fontX + xOffset, temp.y + fontY + yOffset);

            super.render(batch);
        }
    }

}
