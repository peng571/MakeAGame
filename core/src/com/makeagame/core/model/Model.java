package com.makeagame.core.model;

import org.json.JSONException;
import org.json.JSONObject;

public interface Model {

	// ���ܤ����欰
	void process(int command, JSONObject json) throws JSONException;

	// �����X����
	String hold();

	String info();
}
