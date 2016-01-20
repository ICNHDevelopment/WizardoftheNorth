package com.icnhdevelopment.wotn.handlers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.Game;

import static com.badlogic.gdx.Gdx.*;

/**
 * Created by kyle on 5/30/15.
 * Most of this code should be self-explanatory.
 * Since LibGdx renders the bottom right as (0, 0), we have to invert the Y axis for mouse position.
 */

public class CInputProcessor implements InputProcessor {

    private boolean lastButtonLeft = false, buttonLeft = false;
    private boolean lastButtonRight = false, buttonRight = false;
    private Vector2 mousePosition = Vector2.Zero;

    public CInputProcessor () {

    }

    public void update() {
        mousePosition = new Vector2(input.getX(), Game.HEIGHT()-input.getY());

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

    public boolean mouseHovered(float x, float y, float width, float height) {
        return (new Rectangle(x, y, width, height).contains(new Rectangle(getMousePosition().x, getMousePosition().y, 1, 1)));
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

    public Vector2 getMousePosition() {
        return mousePosition;
    }
}
