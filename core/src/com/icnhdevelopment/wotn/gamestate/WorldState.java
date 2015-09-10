package com.icnhdevelopment.wotn.gamestate;

import com.icnhdevelopment.wotn.Game;

/**
 * Created by kyle on 6/4/15.
 */
public class WorldState extends GameState {

    PauseState pauseMenu;
    
    public WorldState(Game g) {
        super(g);

        stateType = GameStates.WORLD;
    }

    @Override
    public void init() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void update() {

    }

    @Override
    public void draw() {

    }
}
