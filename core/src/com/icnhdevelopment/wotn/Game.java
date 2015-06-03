package com.icnhdevelopment.wotn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.gui.*;
import com.icnhdevelopment.wotn.gui.Container;
import com.icnhdevelopment.wotn.gui.Label;
import com.icnhdevelopment.wotn.handlers.CInputProcessor;

public class Game extends ApplicationAdapter {

	SpriteBatch batch;
	CInputProcessor inputProcessor;
	Container main;

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
		batch = new SpriteBatch();
		inputProcessor = new CInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		main = new Container();
		Label l = new Label(main, new Vector2(0, Game.HEIGHT()-44), new Vector2(Game.WIDTH(), 40), "The Wizard of the North");
		l.setFontSize(40);
		l.setColor(Color.WHITE);
		l.setBorderColor(Color.RED);
		l.sethAlignment(Alignment.CENTER);
		l.setBackColor(Color.BLUE);
		l.setBackColorOpacity(1f);
		l.createFont(false);
		Button b = new Button(main, new Vector2(0, 0), new Vector2(2, 2), "Exit") {
			@Override
			public void Click() {
				Game.EXIT();
			}
		};
		b.setFontSize(30);
		b.setColor(Color.BLACK);
		b.setBorderColor(Color.WHITE);
		b.setBackColor(Color.YELLOW);
		b.createFont(true);
		main.buttons.add(b);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		batch.begin();
		main.renderBackground(batch);
		batch.end();
		batch.begin();
		main.render(batch);
		batch.end();
		inputProcessor.update();
		for (Button exit : main.buttons) {
			Vector2 exitPos = exit.getAbsolutePosition();
			Vector2 exitSize = exit.getSize();
			if (inputProcessor.didMouseClick() && inputProcessor.mouseHovered(exitPos.x, exitPos.y, exitSize.x, exitSize.y)) {
				exit.Click();
			}
		}
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
