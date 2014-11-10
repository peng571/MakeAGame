package com.makeagame.first;

import com.badlogic.gdx.graphics.Color;

public class Config {
	
	public static  int  WIDTH = 480;
	public 	static int HEIGHT = 480;
	public static float ratio = 1f;
	public static Color BACKGROUND_COLOR = new Color(1,1,1,1);
	public static int screamWidth()
	{
		return (int)(WIDTH * ratio);
	}
	
	public static int screamHeight()
	{
		return (int)(HEIGHT * ratio);
	}
	

}
