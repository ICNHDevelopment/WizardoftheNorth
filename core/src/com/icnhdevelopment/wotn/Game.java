package com.icnhdevelopment.wotn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icnhdevelopment.wotn.gui.*;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;
import com.icnhdevelopment.wotn.handlers.ColorCodes;

public class Game extends ApplicationAdapter {

	SpriteBatch batch;
	CInputProcessor inputProcessor;
	static Menu currentMenu;

	static int WIDTH, HEIGHT;

	public static int WIDTH() {
		return WIDTH;
	}

	public static int HEIGHT() {
		return HEIGHT;
	}

	public static void EXIT() { Gdx.app.exit(); }

	@Override
	public void create () {
		System.out.println(ColorCodes.GREEN + "Initializing The Wizard of the North...");
		batch = new SpriteBatch();
		inputProcessor = new CInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		currentMenu = new Menu();
		currentMenu.init();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		currentMenu.update(inputProcessor);
		batch.setProjectionMatrix(currentMenu.mainContainer.getRenderCam().combined);
		currentMenu.render(batch);
		inputProcessor.update();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
