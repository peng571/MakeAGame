package com.makeagame.core.model;

public interface Model {

	// 改變內部行為
	void process(String gsonString);

	// 完整交出內部
	String hold();

	String info();
}
