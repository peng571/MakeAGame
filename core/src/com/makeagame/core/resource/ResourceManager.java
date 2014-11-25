package com.makeagame.core.resource;

import java.util.HashMap;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.makeagame.core.Engine;

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

	public TextureRegion fetch(String id) {
		if (resourceMap.get(id) == null) {
			Engine.logE("can't find resource at '" + id + "'");
			return null;
		}
		return resourceMap.get(id).texture;
	}

	public String read(String id) {
		if (resourceMap.get(id) == null) {
			Engine.logE("can't find resource at '" + id + "'");
			return "";
		}
		return resourceMap.get(id).file;
	}

	public void bind(String id, Resource resource) {
		resourceMap.put(id, resource);
	}

}
