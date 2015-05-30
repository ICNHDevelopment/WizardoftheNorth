package com.icnhdevelopment.wotn.gamestate;

import com.icnhdevelopment.wotn.Game;
import com.icnhdevelopment.wotn.gui.Container;

/**
 * Created by kyle on 5/30/15.
 */
public abstract class GameState {

    Game game;
    GameStates stateType;
    public Game getGame() {
        return game;
    }

    public GameState(Game g) {
        game = g;
    }

    public abstract void init();
    public abstract void dispose();

    public abstract void pause();
    public abstract void resume();

    public abstract void update();
    public abstract void draw();

}

