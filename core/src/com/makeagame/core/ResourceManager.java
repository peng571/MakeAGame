package com.makeagame.core;

// this version is base on Libgdx
public class ResourceManager {

	public static ResourceManager instance;

	Resource res[];
	
	private ResourceManager() {
	}

	public static ResourceManager get() {
		if (instance == null) {
			instance = new ResourceManager();
		}
		return instance;
	}
	
	
	// 切換resource
	public Resource use(String s)
	{
		return new Resource();

	}
	
	// 回傳一個元件
	public Object fetch(String s)
	{
		return null;
	}
	
	
	
	class Resource
	{
		int TYPE_IMAGE = 0x001;
		int TYPE_ATTRIBUTE = 0x002;
		int TYPE_WORD = 0x003;
		int TYPE_FILE = 0;
		
		
		
		
	}

}
