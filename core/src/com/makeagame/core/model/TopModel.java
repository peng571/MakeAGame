package com.makeagame.core.model;

import com.badlogic.gdx.utils.JsonValue;


public interface TopModel {

//	Model setFromJSON(JsonValue json);
	
	// ���ܤ����欰
	void process(Object[] signs);

	// �����X����
	Object hold();
}
