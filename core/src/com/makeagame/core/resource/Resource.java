package com.makeagame.core.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Resource {
	TextureRegion texture;
	String file;

	public Resource image(String texture) {
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

	public Resource src(int x, int y, int w, int h)	{
		texture.setRegion(x, y, w, h);
		return this;
	}
}