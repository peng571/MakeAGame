package com.makeagame.core.view;

public class RenderEvent {

	String imgRes;
	float x;
	float y;
	float w;
	float h;

	public RenderEvent(String imgRes, float x, float y) {
		this.imgRes = imgRes;
		this.x = x;
		this.y = y;
	}

}
