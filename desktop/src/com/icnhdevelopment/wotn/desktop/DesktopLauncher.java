package com.icnhdevelopment.wotn.desktop;

	import com.badlogic.gdx.Files;
	import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.icnhdevelopment.wotn.Game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "The Wizard of the North";
		config.width = 800;
		config.height = 600;
		
		new LwjglApplication(new Game(), config);
	}
}
