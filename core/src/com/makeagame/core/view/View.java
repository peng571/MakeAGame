package com.makeagame.core.view;

import java.util.ArrayList;

import org.json.JSONException;

public interface View {

	// �����~�����O
	public abstract void signal(ArrayList<SignalEvent> s) throws JSONException;

	// �e�Xø�ϫ��O
	public abstract ArrayList<RenderEvent> render(ArrayList<String> list);
	
	public abstract String info();
}
