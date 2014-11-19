package com.makeagame.core.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

public class Resource {
	Texture texture;
	String file;

	public Resource image(String texture) {
		this.texture = new Texture(texture);
		return this;
	}

	public Resource attribute(String file) {
		FileHandle handle = Gdx.files.internal(file);
		if (handle != null && handle.exists()) {
			this.file = handle.readString();
		}
		return this;
	}
}