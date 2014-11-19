package com.makeagame.core.resource;

import java.util.HashMap;

import com.badlogic.gdx.graphics.Texture;

public class ResourceManager {

	public static ResourceManager instance;
	private HashMap<String, Resource> resourceMap;

	String url;

	private ResourceManager() {
		resourceMap = new HashMap<String, Resource>();
	}

	public static ResourceManager get() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}

	public Resource use(String id) {
		return resourceMap.get(id);
	}

	// 回傳一個元件
	public Texture fetch(String id) {
		return resourceMap.get(id).texture;
	}

	public String read(String id) {
		return resourceMap.get(id).file;
	}

	public void bind(String id, Resource resource) {
		resourceMap.put(id, resource);
	}

}
