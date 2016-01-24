package com.icnhdevelopment.wotn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.gui.Menu;
import com.icnhdevelopment.wotn.handlers.*;
import com.icnhdevelopment.wotn.items.Item;
import com.icnhdevelopment.wotn.world.World;

import java.awt.*;

public class Game extends ApplicationAdapter {

	SpriteBatch batch;
	CInputProcessor inputProcessor;
	static Menu currentMenu;
	public static World currentWorld;
	public static Battle currentBattle;
	public static OpeningSequence os;
	public static Game game;
	public static GameState GAME_STATE;
	public static SoundHandler soundHandler;
	Texture mouseCursor;

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
		System.out.println(ColorCodes.GREEN + "Initializing The Wizard of the North..." + ColorCodes.RESET);
		game = this;
		batch = new SpriteBatch();
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		moveMouse();
		inputProcessor = new CInputProcessor();
		Gdx.input.setInputProcessor(inputProcessor);
		Gdx.input.setCursorCatched(true);
		//Gdx.input.setCursorPosition(WIDTH/2, HEIGHT/5*4);
		GAME_STATE = GameState.MENU;
		currentMenu = new Menu();
		currentMenu.init("ui/Menus/MNUMain.txt");
		currentWorld = new World();
		currentBattle = new Battle();
		mouseCursor = new Texture("ui/cursor.png");
		os = new OpeningSequence();
		soundHandler = new SoundHandler();
		soundHandler.PlaySoundLooping(Gdx.audio.newSound(Gdx.files.internal("audio/titleMusic.wav")));
		Item.InitItems();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		if (GAME_STATE.equals(GameState.MENU)){
			currentMenu.update(inputProcessor);
			batch.setProjectionMatrix(currentMenu.mainContainer.getRenderCam().combined);
			currentMenu.render(batch);
		} else if (GAME_STATE.equals(GameState.OPENING)){
			os.update(inputProcessor);
			os.render(batch);
		} else if (GAME_STATE.equals(GameState.WORLD)){
			currentWorld.update(inputProcessor);
			currentWorld.render(batch);
		} else if (GAME_STATE.equals(GameState.BATTLE)){
			currentBattle.update(inputProcessor);
			currentBattle.render(batch);
		}
		inputProcessor.update();

		batch.setProjectionMatrix(currentMenu.mainContainer.getRenderCam().combined);
		batch.begin();
		batch.draw(mouseCursor, inputProcessor.getMousePosition().x, inputProcessor.getMousePosition().y-20, 20, 20);
		batch.end();

		Gdx.gl.glDisable(GL20.GL_BLEND);
	}

	public void moveMouse() {
		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		try {
			Robot r = new Robot();
			r.mouseMove(width/2, height/2);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		return;
	}
}
