package com.icnhdevelopment.wotn.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icnhdevelopment.wotn.Game;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Wizard of the North";
		config.resizable = false;
		config.width = 1280;
		config.height = 720;
		//config.fullscreen = true;
		
		new LwjglApplication(new Game(), config);
	}
}
