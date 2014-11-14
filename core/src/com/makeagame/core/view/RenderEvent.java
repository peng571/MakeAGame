package com.makeagame.core.view;

public class RenderEvent {

	public int type;
	public String s;
	public float x;
	public float y;
	public float w;
	public float h;

	public static final int IMAGE = 0x001;
	public static final int LABEL = 0x002;

	public RenderEvent(int type, String s, float x, float y) {
		this.type = type;
		this.s = s;
		this.x = x;
		this.y = y;
	}

}
