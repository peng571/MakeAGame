package com.makeagame.first;

import com.badlogic.gdx.graphics.Texture;
import com.makeagame.core.model.ModelManager;

public  abstract class GameRole extends ModelManager {
	Texture image;
	float x, y;
	float speedX, speedY;
	float maxSpeedX, maxSpeedY;
	float a;
	int w, h;

	GameRole(Texture image) {
		this.image = image;
	}


	void reset() {
		x = 0;
		y = 0;
		speedX = 0;
		speedY = 0;
	}

	
}
