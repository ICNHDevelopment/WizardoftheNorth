package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.handlers.ColorCodes;

/**
 * Created by kyle on 5/31/15.
 */
public class Label extends Container{

    String text;
    int fontsize = 12;
    Color color = Color.WHITE;
    Color bordercolor = Color.WHITE;
    /**
     * halignment determines the horizontal alignment. Should be LEFT, CENTER, or RIGHT
     * valignment determines the vertical alignment. Should be TOP, MIDDLE, or BOTTOM
     */
    Alignment halignment = Alignment.LEFT;
    Alignment valignment = Alignment.BOTTOM;
    String fontType = Fonts.OPEN_SANS;
    BitmapFont font;
    int fontX = 0, fontY = 0;
    float fontWidth, fontHeight;
    boolean usefontsize;

    public void setText(String text) {
        this.text = text;
    }

    public void setUsefontsize(boolean usefontsize) {
        this.usefontsize = usefontsize;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    public void setBordercolor(Color bordercolor) {
        this.bordercolor = bordercolor;
    }

    public void sethalignment(Alignment halignment) {
        this.halignment = halignment;
    }

    public void setvalignment(Alignment valignment) {
        this.valignment = valignment;
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
    }

    public Label(){
        super();
    }

    /**
     * Constructor yay
     *
     * @param pa   The parent container
     * @param pos  Position in the parent container
     * @param sz   The button will default to the size of the text. This should be used to add to width and height.
     * @param text Kind of obvious what this does
     */
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
        font = Fonts.loadFont(Fonts.OPEN_SANS, fontsize, color, bordercolor);
        BitmapFont.TextBounds bounds = font.getBounds(text);
        fontWidth = bounds.width;
        fontHeight = bounds.height;
        if (usefontsize) {
            size.x = fontWidth + size.x + (fontsize / 12f);
            size.y = fontHeight + size.y + (fontsize / 12f);
        }
        setSizeScale();
        alignText();
    }

    void alignText() {
        BitmapFont.TextBounds bounds = font.getBounds(text);
        fontWidth = bounds.width;
        fontHeight = bounds.height;
        float borderSize = 0;
        if (bordercolor != color) {
            borderSize = fontsize / 12f;
        }
        if (halignment == Alignment.CENTER) { //Centered horizontally
            fontX = (int) ((super.size.x - (fontWidth + borderSize * font.getScaleX() * 2)) / 2);
        } else if (halignment == Alignment.RIGHT) {
            fontX = (int) (super.size.x - (fontWidth + borderSize * font.getScaleX()));
        } else {
            fontX = 0;//(int)(borderSize*font.getScaleX()/2);
        }
        if (valignment == Alignment.MIDDLE) { //Centered vertically
            fontY = (int) ((super.size.y - (fontHeight + borderSize * font.getScaleY() * 2)) / 2);
        } else if (valignment == Alignment.TOP) {
            fontY = (int) (size.y - (fontHeight + borderSize * font.getScaleY()));
        } else {
            fontY = 0;//(int)(borderSize*font.getScaleY()/2);
        }
    }

    public void resize(Vector2 old, Vector2 newS) {
        super.resize();

        float xDiff = newS.x / old.x;
        float yDiff = newS.y / old.y;
        font.setScale(Math.abs(font.getScaleX() * xDiff), Math.abs(font.getScaleY() * yDiff));
        alignText();
    }

    void renderText(SpriteBatch batch) {
        if (font == null) {
            System.out.println(ColorCodes.RED + "Must call Label.createFont before rendering Label or any subclass" + ColorCodes.CYAN + " in Label " + getName());
        }
        float yOffset = fontHeight, xOffset = 0;
        if (color != bordercolor) {
            yOffset += fontsize / 12f;// * font.getScaleY();
            xOffset += fontsize / 12f;// * font.getScaleX();
        }
        Vector2 temp = getAbsolutePosition();
        batch.begin();
        font.draw(batch, text, temp.x + fontX + xOffset, temp.y + fontY + yOffset);
        batch.end();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (visible) {
            renderBackground(batch);
            renderText(batch);

            renderChildren(batch);
        }
    }

}
