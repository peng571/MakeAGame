package com.makeagame.core.model;

import com.badlogic.gdx.utils.JsonValue;


public interface TopModel {

//	Model setFromJSON(JsonValue json);
	
	// 改變內部行為
	void process(Object[] signs);

	// 完整交出內部
	Object hold();
}
