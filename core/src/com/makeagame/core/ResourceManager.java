package com.makeagame.core;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;

// this version is base on Libgdx
public class ResourceManager {

	public static ResourceManager instance;
	private HashMap<String, Texture> textureMap;
	private HashMap<String, String> initMap;

	String url;

	private ResourceManager() {
		textureMap = new HashMap<String, Texture>();
		initMap = new HashMap<String, String>();
	}

	public static ResourceManager get() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}

	// 切換resource
	public ResourceManager use(String s) {
		this.url = s;
		return this;
	}

	// 回傳一個元件
	public Texture fetch(String id) {
		return textureMap.get(id);
	}

	public String reed(String id) {
		return initMap.get(id);
	}

	public void bind(String id, String texture, String file) {
		FileHandle handle = Gdx.files.internal(file);
		if (handle.exists()) {
			initMap.put(id, handle.readString());
		}
		Texture t = new Texture(texture);
		if (t != null) {
			textureMap.put(id, t);
		}
	}

}
