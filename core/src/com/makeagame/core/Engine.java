package com.makeagame.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Engine {

	SpriteBatch batch = new SpriteBatch();
	
	public void start()
	{
		TopControler.get().loop();
	}
	
}
