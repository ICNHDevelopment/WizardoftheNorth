package com.icnhdevelopment.wotn.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

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
    Vector2 positionScale;
    Vector2 sizeScale;
    protected boolean visible = true;
    protected boolean isHovered = false;
    Color backcolor = null;

    protected ArrayList<Container> children;
    public ArrayList<Button> buttons;

    ShapeRenderer shapeRenderer;
    OrthographicCamera renderCam;

    public static Container getContainer(String typeName){
        if (typeName.equals("Label")) return new Label();
        else if (typeName.equals("ImageLabel")) return new ImageLabel();
        return null;
    }

    /**
     * When creating interfaces, use this constructor to create a parent Container the size of the screen.
     */
    public Container() {
        init(null, Vector2.Zero, new Vector2(Game.WIDTH(), Game.HEIGHT()));
    }

    /**
     * This constructor automatically adds itself to it's parent, so there is no need to to it manually.
     * The size set will be in pixels, so it might not work on some screens.
     * LibGdx automatically resizes elements when the window is resized.
     *
     * @param pa  - The parent Container
     * @param pos - The position of the Container ((0, 0) is at the bottom left).
     * @param sz  - The size of the Container in pixels. Use Container(Container pa, Vector2 pos, double percX, double percY) for percentage setup.
     */
    public Container(Container pa, Vector2 pos, Vector2 sz) {
        init(pa, pos, sz);

        parent.addChild(this);
    }

    void init(Container pa, Vector2 pos, Vector2 sz) {
        parent = pa;
        position = pos;
        size = sz;
        setSizeScale();

        children = new ArrayList<>();
        shapeRenderer = new ShapeRenderer();
        buttons = new ArrayList<>();
        if (parent == null) {
            renderCam = new OrthographicCamera(size.x, size.y);
            renderCam.position.set(Game.WIDTH() / 2, Game.HEIGHT() / 2, 0);
            renderCam.update();
        }
    }

    void setSizeScale() {
        if (parent == null) {
            sizeScale = new Vector2(1f, 1f);
            positionScale = new Vector2(0f, 0f);
        } else {
            Rectangle paRec = parent.getBounds();
            float wS = size.x / paRec.width;
            float hS = size.y / paRec.height;
            sizeScale = new Vector2(wS, hS);
            float xS = position.x / paRec.width;
            float yS = position.y / paRec.height;
            positionScale = new Vector2(xS, yS);
        }
    }

    public void reposition() {
        if (parent != null) {
            Vector2 paPos = parent.getSize();
            position = new Vector2(paPos.x * positionScale.x, paPos.y * positionScale.y);
        }
        for (Container child : children) {
            child.reposition();
            if (child instanceof ImageLabel) {
                ImageLabel c = (ImageLabel) child;
                c.setImagealignment(c.imagealignment);
            }
        }
    }

    public void resize() {
        Vector2 oldT = size;
        Vector2 newT = size;
        if (parent != null) {
            Rectangle paRec = parent.getBounds();
            size = new Vector2(paRec.width * sizeScale.x, paRec.height * sizeScale.y);
            newT = size;
        }
        for (Container child : children) {
            if (child instanceof Label) {
                ((Label) child).resize(oldT, newT);
            } else {
                child.resize();
            }
        }
    }

    public void reposition(float x, float y) {
        position = new Vector2(x, y);
    }

    public void resize(float x, float y) {
        Vector2 oldT = size;
        size = new Vector2(x, y);
        Vector2 newT = size;
        if (parent != null) {
            Vector2 paRec = parent.getSize();
            float wS = size.x / paRec.x;
            float hS = size.y / paRec.y;
            sizeScale = new Vector2(wS, hS);
        }
        for (Container child : children) {
            if (child instanceof Label) {
                ((Label) child).resize(oldT, newT);
            } else {
                child.resize();
            }
            child.reposition();
        }
    }

    /**
     * Self explanatory. Adds the child to the children array.
     *
     * @param c Container object
     */
    void addChild(Container c) {
        children.add(c);
    }

    public void updateChildren(CInputProcessor processor) {
        for (Button button : buttons) {
            Container but = (Container)button;
            Vector2 butPos = but.getAbsolutePosition();
            Vector2 butSize = but.getSize();
            if (processor.mouseHovered(butPos.x, butPos.y, butSize.x, butSize.y)) {
                ((Container) button).isHovered = true;
                if (processor.didMouseClick()) {
                    button.Click();
                }
            }else{
                ((Container) button).isHovered = false;
            }
        }
    }

    public void renderBackground(SpriteBatch batch) {
        if (backcolor != null) {
            batch.begin();
            shapeRenderer.begin(ShapeType.Filled);
            shapeRenderer.setColor(backcolor.r, backcolor.g, backcolor.b, 1);
            Vector2 temp = getAbsolutePosition();
            shapeRenderer.rect(temp.x, temp.y, size.x, size.y);
            shapeRenderer.end();
            batch.end();
        }
    }

    protected void renderChildren(SpriteBatch batch) {
        for (Container child : children) {
            child.render(batch);
        }
    }

    /**
     * The render method first draws itself if required, then calls the render method of each child.
     * Children will be drawn on top of the parent.
     *
     * @param batch - SpriteBatch to render images
     */
    public void render(SpriteBatch batch) {
        if (visible) {
            renderBackground(batch);
            renderChildren(batch);
        }
    }

    /**
     * Gets the position within the parent, basically the position field.
     *
     * @return position within the parent
     */
    public Vector2 getRelativePosition() {
        return position;
    }

    /**
     * UNIMPLEMENTED
     * Gets the absolute position of the element in the game window.
     * Should be used for human interaction.
     *
     * @return position on the screen
     */
    public Vector2 getAbsolutePosition() {
        Vector2 temp = Vector2.Zero;
        if (parent != null) {
            temp = parent.getAbsolutePosition();
        }
        return new Vector2(temp.x + position.x, temp.y + position.y);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Vector2 getSize() {
        return size;
    }

    public Rectangle getBounds() {
        return new Rectangle(position.x, position.y, size.x, size.y);
    }

    public void setBackcolor(Color c) {
        backcolor = c;
    }

    public Container getParent() {
        if (parent == null) return this;
        return parent;
    }

    public Container getAbsoluteParent() {
        if (parent == null) return this;
        return getParent().getAbsoluteParent();
    }

    public OrthographicCamera getRenderCam() {
        return renderCam;
    }

}
