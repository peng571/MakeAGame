package com.makeagame.core;

import com.badlogic.gdx.graphics.Color;
import com.makeagame.core.model.ModelManager;
import com.makeagame.core.view.ViewManager;

public abstract class Bootstrap {

	public static boolean LOG = true;
	public static boolean DEBUG = true;

	public static float ratio = 1f;
	public static Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);

	public abstract int getScreenWidth();

	public abstract int getScreenHeight();

	public abstract void viewFactory(ViewManager manager);

	public abstract void modelFactory(ModelManager manager);

	public abstract void resourceFactory(ResourceManager resource);

	public int screamWidth() {
		return (int) (getScreenWidth() * ratio);
	}

	public int screamHeight() {
		return (int) (getScreenHeight() * ratio);
	}

	public static void logI(String s) {
		if (LOG) {
			System.out.println(s);
		}
	}

	public static void logD(String d) {
		if (LOG) {
			System.out.println(d);
		}
	}

	public static void logE(String e) {
		System.out.println(e);
	}

	public static void logE(Exception e) {
		System.out.println(e);
	}

}
