package com.icnhdevelopment.wotn;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.icnhdevelopment.wotn.battle.Battle;
import com.icnhdevelopment.wotn.gui.Menu;
import com.icnhdevelopment.wotn.handlers.*;
import com.icnhdevelopment.wotn.items.Item;
import com.icnhdevelopment.wotn.world.World;

import java.awt.*;
import java.util.Random;

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
	Random random;
	long lightningTime = 0, lightningTimeNext = 0;
	boolean drawLightning = false;
	Vector2 startPos, endPos1, endPos2;

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
		GAME_STATE = GameState.MENU;
		currentMenu = new Menu();
		currentMenu.init("ui/Menus/MNUMain.txt");
		currentWorld = new World();
		currentBattle = new Battle();
		mouseCursor = new Texture("ui/cursor.png");
		os = new OpeningSequence();
		soundHandler = new SoundHandler();
		soundHandler.PlaySoundLooping(Gdx.audio.newSound(Gdx.files.internal("audio/rain.wav")));
		Item.InitItems();
		random = new Random();
		GFX.setTexture(new Texture("ui/hud/ExperienceMeter.png"));
		lightningTimeNext = 500 + (long)(random.nextDouble()*3000L);
		lightningTime = System.currentTimeMillis();
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
			if (drawLightning) {
				batch.begin();
				GFX.drawChainLightningRandomBetweenPoints(batch, startPos, endPos1, endPos2, 3f, 3, Color.WHITE, new Color(255f/255f, 216f/255f, 0f/255f, 1f));//, Color.YELLOW, Color.ORANGE);
				batch.end();
				if (System.currentTimeMillis()-lightningTime>lightningTimeNext){
					lightningTimeNext = 500 + (long)(random.nextDouble()*7000L);
					lightningTime = System.currentTimeMillis();
					drawLightning = false;
				}
			}else{
				batch.setColor(Color.WHITE);
				if (System.currentTimeMillis()-lightningTime>lightningTimeNext){
					startPos = new Vector2(random.nextInt(1280), random.nextInt(720));
					endPos1 = new Vector2(random.nextInt(1280), random.nextInt(720));
					endPos2 = new Vector2(random.nextInt(1280), random.nextInt(720));
					while (WizardHelper.getDistanceFromPoint(endPos1, endPos2)>40){
						endPos2 = new Vector2(random.nextInt(1280), random.nextInt(720));
					}
					drawLightning = true;
					lightningTimeNext = 500L;
					lightningTime = System.currentTimeMillis();
					Gdx.audio.newSound(Gdx.files.internal("audio/lightning.wav")).play();

				}
			}
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
		Vector2 mouse = inputProcessor.getMousePosition();
		if (mouse.x>1270){
			Gdx.input.setCursorPosition(1270, HEIGHT-(int)mouse.y);
		} else if (mouse.x<0) {
			Gdx.input.setCursorPosition(0, HEIGHT-(int)mouse.y);
		}
		if (mouse.y>710){
			Gdx.input.setCursorPosition((int)mouse.x, 0);
		} else if (mouse.y<0){
			Gdx.input.setCursorPosition((int)mouse.x, 710);
		}

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
