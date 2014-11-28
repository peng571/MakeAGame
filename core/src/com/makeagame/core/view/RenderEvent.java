package com.makeagame.core.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class RenderEvent {

	public int type;
	public String s;
	public float x;
	public float y;
	public int srcX;
	public int srcY;
	public int srcW;
	public int srcH;
	public int dstW;
	public int dstH;
	public float ratioX;
	public float ratioY;
	public float angle;
	public int gravity;
	public Color color;
	public int size;
	public TextureRegion texture;
	public static final int IMAGE = 0x001;
	public static final int LABEL = 0x002;

	public static final int LEFT = 0x000;
	public static final int RIGHT = 0x010;
	public static final int CENTER = 0x001;
	public static final int TOP = 0x100;
	public static final int DOWN = 0x000;

	private RenderEvent() {
		angle = 0;
		XY(0, 0);
		srcWH(36, 36);
		gravity = 0;
	}

	public RenderEvent(String s) {
		this();
		this.type = LABEL;
		this.s = s;
		this.color = new Color(Color.BLACK);
	}

	public RenderEvent(TextureRegion texture) {
		this();
		this.type = IMAGE;
		this.texture = texture;
	}

	public RenderEvent XY(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public RenderEvent srcWH(int w, int h) {
		this.srcW = w;
		this.srcH = h;
		this.dstH = h;
		this.dstW = w;
		this.ratioX = 1f;
		this.ratioY = 1f;
		return this;
	}
	
	public RenderEvent src(int x, int y, int w, int h) {
		this.srcX = x;
		this.srcY = y;
		this.srcW = w;
		this.srcH = h;
		this.dstH = h;
		this.dstW = w;
		this.ratioX = 1f;
		this.ratioY = 1f;
		return this;
	}

	public RenderEvent dstWH(int w, int h) {
		this.dstH = h;
		this.dstW = w;
		this.ratioX = dstW / srcW;
		this.ratioY = dstH / srcH;
		return this;
	}

	public RenderEvent Ratio(float r) {
		this.ratioX = r;
		this.ratioY = r;
		this.dstH = (int) (srcH * r);
		this.dstW = (int) (srcW * r);
		return this;
	}

	public RenderEvent Rotation(float angle) {
		this.angle = angle;
		return this;
	}

	public RenderEvent Gravity(int gravity) {
		this.gravity = gravity;
		return this;
	}

	public RenderEvent filp(boolean x, boolean y) {
		texture.flip(x, y);
		return this;
	}

	
	public RenderEvent color(int r, int g, int b, int a)	{
		color = new Color(r, g, b, a);
		return this;
	}
	
	public RenderEvent size(int size)	{
		this.size = size;
		return this;
	}

}
