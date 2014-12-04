package com.makeagame.core.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resource {
	TextureRegion texture;
	int centerY;
	int x, y, w, h;
	String file;

	public Resource image( String texture) {
		this.texture = new TextureRegion(new Texture(texture));
		return this;
	}

	public Resource attribute(String file) {
		FileHandle handle = Gdx.files.internal(file);
		if (handle != null && handle.exists()) {
			this.file = handle.readString();
		}
		return this;
	}

//	public Resource src(int srcX, int srcY, int srcW, int srcH) {
//		texture.setRegion(srcX, srcY, srcW, srcH);
//		return this;
//	}
////
//	public Resource src(int srcW, int srcH) {
//		return src(0, 0, srcW, srcH);
//	}
//
//	public Resource dst(int dstX, int dstY, int dstW, int dstH) {
//		this.x = dstX;
//		this.y = dstY;
//		this.w = dstW;
//		this.h = dstH;
//		return this;
//	}
//
//	public Resource center(int x, int y) {
//		this.centerX = x;
//		this.centerY = y;
//		return this;
//	}
}