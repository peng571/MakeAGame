package com.makeagame.core;

import com.badlogic.gdx.graphics.Color;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.resource.ResourceManager;
import com.makeagame.core.view.ViewManager;

public abstract class Bootstrap {

	public static final int FPS = 60;
	private static final int WIDTH = 960;
	private static final int HEIGHT = 640;
	public static float ratio = 1f;
	public static Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);

	public abstract void viewFactory(ViewManager manager);

	public abstract void modelFactory(ModelManager manager);

	public abstract void resourceFactory(ResourceManager resource);

	public static int screamWidth() {
		return (int) (WIDTH * ratio);
	}

	public static int screamHeight() {
		return (int) (HEIGHT * ratio);
	}

}
