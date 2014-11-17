package com.makeagame.core;

import com.badlogic.gdx.graphics.Color;

public abstract class Bootstrap {

	public static boolean LOG = true;
	public static boolean DEBUG = true;

	public static int WIDTH = 480;
	public static int HEIGHT = 480;
	public static float ratio = 1f;
	public static Color BACKGROUND_COLOR = new Color(1, 1, 1, 1);

	public abstract void setResource();

	public void setDevice(int w, int h) {
		WIDTH = w;
		HEIGHT = h;
	}

	public abstract void createView();

	public abstract void createModel();

	public static int screamWidth() {
		return (int) (WIDTH * ratio);
	}

	public static int screamHeight() {
		return (int) (HEIGHT * ratio);
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
