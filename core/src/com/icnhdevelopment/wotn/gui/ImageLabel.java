package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by kyle on 6/5/15.
 */
public class ImageLabel extends Container implements Button {

    Texture image;
    String texturefile;
    Alignment imagealignment = Alignment.SINGLE;
    Vector2 imagePosition, imageSize;

    public ImageLabel(Container pa, Vector2 pos, Vector2 sz) {
        super(pa, pos, sz);

        this.image = new Texture(texturefile);
        imageSize = new Vector2(image.getWidth(), image.getHeight());
    }

    public ImageLabel(Container pa, Vector2 pos, Vector2 sz, Texture im) {
        super(pa, pos, sz);

        this.image = im;
        imageSize = new Vector2(im.getWidth(), im.getHeight());
    }

    public ImageLabel(){
        super();
    }

    /**
     * Set the alignment of the image within its bounds.
     * @param a Use SINGLE, CENTER, TILED, or STRETCHED; The other Alignments are for text.
     */
    public void setImagealignment(Alignment a) {
        this.imagealignment = a;

        setImagePositioning();
    }

    void setImagePositioning() {
        switch (imagealignment) {
            case CENTER:
            {
                imagePosition = new Vector2(0-(imageSize.x-size.x)/2, 0-(imageSize.y-size.y)/2);
            }
            break;
            default:
            {
                imagePosition = Vector2.Zero;
            }
        }
    }

    public void resize(){
        super.resize();

        setImagealignment(imagealignment);
    }

    void renderImage(SpriteBatch batch) {
        batch.begin();
        if (imagealignment.equals(Alignment.STRETCHED)) {
            imageSize = size;
        }
        if (imagealignment.equals(Alignment.TILED)) {
            /*
            My Dearest Albert,
                Here you can see a bunch of math. It was a fucking pain in the ass
            to write. It simply goes through and draws the image regularly each time
            that it fits. Then when it goes outside the container, it only draws the
            part of the image that fits. This is probably the most useless code in
            the entire game and I bet it is never used.
                                Your Dearest,
                                Kyle
             */
            int maxWidth = (int) Math.ceil(size.x / imageSize.x);
            int maxHeight = (int) Math.ceil(size.y / imageSize.y);
            for (int w = 0; w<maxWidth; w++) {
                for (int h = 0; h<maxHeight; h++) {
                    double posX = imageSize.x * w;
                    double posY = imageSize.y * h;
                    TextureRegion reg;
                    float texX = 0, texY = 0, texW = imageSize.x, texH = imageSize.y;
                    if (posX + imageSize.x > size.x) {
                        texW = (size.x - (maxWidth-1)*imageSize.x);
                    }
                    if (posY + imageSize.y > size.y) {
                        texH = (size.y - (maxHeight-1)*imageSize.y);
                    }
                    texY = imageSize.y-texH;
                    reg = new TextureRegion(image, (int)texX, (int)texY, (int)texW, (int)texH);
                    float x = getAbsolutePosition().x+(float)posX;
                    float y = getAbsolutePosition().y+(float)posY;
                    batch.draw(reg, x, y, texW, texH);
                }
            }
        }
        else {
            batch.draw(image, getAbsolutePosition().x + imagePosition.x, getAbsolutePosition().y + imagePosition.y, imageSize.x, imageSize.y);
        }
        batch.end();
    }

    @Override
    public void render(SpriteBatch batch) {
        if (visible) {
            renderBackground(batch);
            renderImage(batch);

            renderChildren(batch);
        }
    }

    @Override
    public void Click() {

    }
}
