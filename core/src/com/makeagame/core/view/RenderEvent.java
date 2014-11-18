package com.makeagame.core.view;

import com.badlogic.gdx.graphics.Texture;

public class RenderEvent {

	public int type;
	public String s;
	public float x;
	public float y;
	public float w;
	public float h;
	public int gravity;
	public Texture texture;
	public static final int IMAGE = 0x001;
	public static final int LABEL = 0x002;

	public static final int LEFT = 0x000;
	public static final int RIGHT = 0x010;
	public static final int CENTER = 0x001;
	public static final int TOP = 0x100;
	public static final int DOWN = 0x000;

	private RenderEvent() {
		x = 0;
		y = 0;
		gravity = 0;
	}

	public RenderEvent(String s) {
		this();
		this.type = LABEL;
		this.s = s;
	}

	public RenderEvent(Texture texture) {
		this();
		this.type = IMAGE;
		this.texture = texture;
	}

	public RenderEvent XY(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public RenderEvent Gravity(int gravity) {
		this.gravity = gravity;
		return this;
	}

}
