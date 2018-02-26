package com.sad.function.rogue.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sad.function.rogue.GdxRenderAndLightingSystemTest;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = 16 * 80;
		config.height = 16 * 50;
//		new LwjglApplication(new RogueLikeGame(), config);
//		new LwjglApplication(new GdxTest(), config);
		new LwjglApplication(new GdxRenderAndLightingSystemTest(), config);
	}
}
