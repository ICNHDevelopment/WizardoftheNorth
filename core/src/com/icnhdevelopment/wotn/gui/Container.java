package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;

import java.util.ArrayList;

/**
 * Created by kyle on 5/30/15.
 * This is the most basic form of an interface element
 * and can't be visually drawn on the screen.
 */
public class Container {

    Container parent;
    Vector2 position;
    Vector2 size;
    boolean visible = true;

    ArrayList<Container> children;

    /**
     * When creating interfaces, use this constructor to create a parent Container the size of the screen.
     */
    public Container() {
        position = Vector2.Zero;
        size = new Vector2(Game.WIDTH, Game.HEIGHT);
        parent = null;

        children = new ArrayList<>();
    }

    /**
     * This constructor automatically adds itself to it's parent, so there is no need to to it manually.
     * The size set will be in pixels, so it might not work on some screens.
     * LibGdx automatically resizes elements when the window is resized.
     * @param pa - The parent Container
     * @param pos - The position of the Container ((0, 0) is at the bottom left).
     * @param sz - The size of the Container in pixels. Use Conatiner(Container pa, Vector2 pos, double percX, double percY) for percentage setup.
     */
    public Container(Container pa, Vector2 pos, Vector2 sz) {
        parent = pa;
        position = pos;
        size = sz;

        parent.addChild(this);
        children = new ArrayList<>();
    }

    /**
     * This constructor automatically adds itself to it's parent, so there is no need to to it manually.
     * The size is set using percentages, so it should scale based on the screen size.
     * LibGdx automatically resizes elements when the window is resized.
     * @param pa - The parent Container
     * @param pos - The position of the Container ((0, 0) is at the bottom left).
     * @param percX - The percent of the width of the parent to set this width to. 1 = 100%, .5 = 50%, etc.
     * @param percY - The percent of the height of the parent to set this height to. 1 = 100%, .5 = 50%, etc.
     */
    public Container(Container pa, Vector2 pos, float percX, float percY) {
        parent = pa;
        position = pos;
        float tempX = Game.WIDTH * percX;
        float tempY = Game.HEIGHT * percY;
        size = new Vector2(tempX, tempY);

        parent.addChild(this);
        children = new ArrayList<>();
    }

    /**
     * Self explanatory. Adds the child to the children array.
     * @param c
     */
    void addChild(Container c) {
        children.add(c);
    }

    /**
     * The render method first draws itself if required, then calls the render method of each child.
     * Children will be drawn on top of the parent.
     * @param batch - SpriteBatch to render images
     */
    public void render(SpriteBatch batch) {
        if (visible) {
            for (Container child : children) {
                child.render(batch);
            }
        }
    }

    /**
     * Gets the position within the parent, basically the position field.
     * @return
     */
    public Vector2 getRelativePosition() {
        return position;
    }

    /**
     * UNIMPLEMENTED
     * Gets the absolute position of the element in the game window.
     * Should be used for human interaction.
     * @return
     */
    public Vector2 getAbsolutePosition() {
        Vector2 temp = Vector2.Zero;
        if (parent != null) {
            temp = parent.getAbsolutePosition();
        }
        return new Vector2(temp.x + position.x, temp.y + position.y);
    }

}
