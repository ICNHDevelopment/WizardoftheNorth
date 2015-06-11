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

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends ApplicationAdapter {

	SpriteBatch batch;
	CInputProcessor inputProcessor;
	static Container main;

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
		l.createFont(false);
		Button b = new Button(main, new Vector2(0, 0), new Vector2(4, 4), "Exit") {
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
		ImageLabel il = new ImageLabel(main, new Vector2(200, 0), new Vector2(400, 600), new Texture(Gdx.files.internal("badlogic.jpg")));
		il.setImageAlignment(Alignment.TILED);
		il.setBackColor(Color.RED);
		il.setBackColorOpacity(.2f);
		b = new Button(il, new Vector2(10, 10), new Vector2(2, 2), "Invisitize") {
			@Override
			public void Click() {
				new Timer().schedule(new TimerTask() {
					@Override
					public void run() {
						if (main.getSize().x > 800) {
							main.getAbsoluteParent().resize(main.getSize().x-16, main.getSize().y);
						}
						if (main.getSize().y > 450) {
							main.getAbsoluteParent().resize(main.getSize().x, main.getSize().y-9);
						}
						if (main.getSize().x <= 800 && main.getSize().y <= 450) {
							this.cancel();
						}
					}
				}, new Date(), 10);
			}
		};
		b.setBackColor(Color.ORANGE);
		b.createFont(true);
		main.buttons.add(b);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		main.updateChildren(inputProcessor);
		batch.begin();
		main.renderBackground(batch);
		batch.end();
		batch.begin();
		main.render(batch);
		batch.end();
		inputProcessor.update();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
}
