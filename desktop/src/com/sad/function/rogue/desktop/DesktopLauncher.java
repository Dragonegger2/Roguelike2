package com.sad.function.rogue.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sad.function.rogue.Globals;
import com.sad.function.rogue.screens.RogueLikeScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Globals.screenWidth;
		config.height = Globals.screenHeight;
		new LwjglApplication(new RogueLikeScreen(), config);
	}
}
