package com.icnhdevelopment.wotn.handlers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;

import static com.badlogic.gdx.Gdx.*;

/**
 * Created by kyle on 5/30/15.
 */

public class CInputProcessor implements InputProcessor {

    boolean lastButtonLeft = false, buttonLeft = false;
    boolean lastButtonRight = false, buttonRight = false;
    Vector2 mousePosition = Vector2.Zero;

    public CInputProcessor () {

    }

    public void update() {
        mousePosition = new Vector2(input.getX(), Game.HEIGHT-input.getY());

        lastButtonLeft = buttonLeft;
        buttonLeft = input.isButtonPressed(Input.Buttons.LEFT);
        lastButtonRight = buttonRight;
        buttonRight = input.isButtonPressed(Input.Buttons.RIGHT);
    }

    public boolean isKeyDown(int keyCode) {
        return input.isKeyPressed(keyCode);
    }

    public boolean didMouseClick() {
        return (!buttonLeft && lastButtonLeft);
    }

    public boolean didRightClick() {
        return (!buttonRight && lastButtonRight);
    }

    public boolean mouseHovered(int x, int y, int width, int height) {
        return (new Rectangle(x, y, width, height).contains(new Rectangle(mousePosition.x, mousePosition.y, 1, 1)));
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
