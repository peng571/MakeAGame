package com.makeagame.core.model;

public interface Model {

	Model init(String gsonString);

	// ���ܤ����欰
	void process(String gsonString);

	// �����X����
	String hold();

	String info();
}
