package com.makeagame.core.model;


public interface TopModel {

	// ���ܤ����欰
	void process(String gsonString);

	// �����X����
	String hold();
	
	public abstract String info();
}
