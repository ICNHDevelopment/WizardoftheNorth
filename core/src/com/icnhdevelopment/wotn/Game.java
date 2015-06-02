package com.icnhdevelopment.wotn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.Alignment;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.Fonts;
import com.icnhdevelopment.wotn.gui.Label;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

import java.awt.*;

public class Game extends ApplicationAdapter {

	SpriteBatch batch;
	Texture img;
	CInputProcessor inputProcessor;
	Container main;

	static int WIDTH, HEIGHT;

	public static int WIDTH() {
		return WIDTH;
	}

	public static int HEIGHT() {
		return HEIGHT;
	}
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		inputProcessor = new CInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		main = new Container();
		Label l = new Label(main, new Vector2(0, Game.HEIGHT()-44), new Vector2(Game.WIDTH(), 40), "The Wizard of the North");
		l.setFontSize(40);
		l.setColor(Color.MAGENTA);
		l.setBorderColor(Color.RED);
		l.sethAlignment(Alignment.CENTER);
		l.createFont();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		//batch.draw(img, 0, 0);
		main.render(batch);
		batch.end();
		inputProcessor.update();
		if (inputProcessor.mouseHovered(0, 0, img.getWidth(), img.getHeight()) && inputProcessor.didMouseClick()) {
			Gdx.app.exit();
		}
	}
}
