package com.icnhdevelopment.wotn.desktop;

	import com.badlogic.gdx.Files;
	import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icnhdevelopment.wotn.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Wizard of the North";
		config.width = 1600;
		config.height = 900;
		
		new LwjglApplication(new Game(), config);
	}
}
