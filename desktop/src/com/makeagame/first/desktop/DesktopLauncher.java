package com.makeagame.first.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.makeagame.first.Config;
import com.makeagame.first.MakeAGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//	     Settings settings = new Settings();
	     config.width = Config.screamWidth();
	        config.height = Config.screamHeight();
//	        TexturePacker.process(settings, "../images", "../game-android/assets", "game");
//
//	        new LwjglApplication(new MakeAGame(), "Game", 320, 480, false);
	  		new LwjglApplication(new MakeAGame(), config);
	}
}
