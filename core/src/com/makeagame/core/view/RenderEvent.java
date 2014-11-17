package com.makeagame.core.view;

import com.badlogic.gdx.graphics.Texture;

public class RenderEvent {

	public int type;
	public String s;
	public float x;
	public float y;
	public float w;
	public float h;
	public Texture texture;
	public static final int IMAGE = 0x001;
	public static final int LABEL = 0x002;

	public RenderEvent( String s, float x, float y) {
		this.type = LABEL;
		this.s = s;
		this.x = x;
		this.y = y;
	}

	public RenderEvent(Texture texture, float x, float y) {
		this.type = IMAGE;
		this.texture = texture;
		this.x = x;
		this.y = y;
	}

}
