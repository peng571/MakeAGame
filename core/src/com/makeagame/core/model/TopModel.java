package com.makeagame.core.model;


public interface TopModel {

	void init(String gsonString);
	
	// ���ܤ����欰
	void process(String gsonString);

	// �����X����
	String hold();
	
	public abstract String info();
}
