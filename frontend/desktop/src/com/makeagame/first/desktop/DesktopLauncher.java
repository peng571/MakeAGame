package com.makeagame.first.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.makeagame.core.Bootstrap;
import com.makeagame.core.MakeAGame;

public class DesktopLauncher {
	public static void main(String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Bootstrap.screamWidth();
		config.height = Bootstrap.screamHeight();
		config.title = "Game";
		// TexturePacker.process(settings, "../images", "../game-android/assets", "game");

		new LwjglApplication(new MakeAGame().getApplication(), config);
	}
}