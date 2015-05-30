package com.icnhdevelopment.wotn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

public class Game extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;
	CInputProcessor inputProcessor;

	public static int WIDTH, HEIGHT;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		inputProcessor = new CInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
		inputProcessor.update();
		if (inputProcessor.mouseHovered(0, 0, img.getWidth(), img.getHeight()) && inputProcessor.didMouseClick()) {
			Gdx.app.exit();
		}
	}
}
